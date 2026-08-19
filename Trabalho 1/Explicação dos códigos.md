# Explicação dos códigos - Módulo 1 e Módulo 2

## Visão geral

Este trabalho foi organizado em arquitetura MVC, separando responsabilidades em camadas para deixar o código mais legível, testável e fácil de evoluir.

No MVC utilizado aqui:

- **Model**: mantém os dados e regras centrais do domínio.
- **View**: exibe mensagens e resultados no console.
- **Controller**: coordena o fluxo da aplicação.
- **Service**: concentra a lógica de processamento e regras de execução.

Os dois módulos usam threads, mas com objetivos diferentes:

- **Módulo 1**: múltiplas threads alteram o mesmo estado compartilhado (saldo central), exigindo sincronização.
- **Módulo 2**: cada thread trabalha com seus próprios dados locais, sem compartilhamento durante o processamento.

---

## Módulo 1 - Com compartilhamento de memória

### Objetivo do módulo

Simular 5 caixas vendendo fichas ao mesmo tempo e atualizando um único saldo central do evento. Como várias threads alteram a mesma variável, é necessário controle de acesso para evitar condição de corrida.

### Arquitetura e classes

#### Main

Classe de entrada da aplicação. Instancia os componentes e conecta as dependências:

1. cria o model (`CaixaModel`);
2. cria o service (`CaixaService`) com o model;
3. cria a view (`CaixaView`);
4. cria o controller (`CaixaController`) e inicia o fluxo.

#### Model - `CaixaModel`

Representa o estado compartilhado do sistema.

- Atributo principal: `saldoCentral`.
- Método `adicionarVenda(double valor)`: soma o valor ao saldo.
- Método `getSaldoCentral()`: retorna o saldo final.

Os dois métodos foram declarados com `synchronized`. Isso garante exclusão mútua: apenas uma thread por vez consegue entrar na região crítica de atualização/leitura sincronizada do saldo.

#### Service - `CaixaService`

Contém a regra de negócio da venda.

- `venderFicha()`: executa 1000 iterações e, em cada uma, adiciona R$ 10,00 ao saldo central por meio do model.
- `verSaldo()`: consulta o saldo final no model.

Essa camada evita que a regra de cálculo fique espalhada no controller.

#### Controller - `CaixaController`

Coordena a concorrência e o encerramento da execução.

1. Cria um conjunto de 5 threads (cada uma representa um caixa).
2. Inicia todas as threads com `start()`.
3. Aguarda todas terminarem com `join()`.
4. Após o término, solicita o saldo ao service e envia para exibição na view.
5. Compara o resultado com o valor esperado de R$ 50.000,00.

#### View - `CaixaView`

Camada de apresentação.

- `exibirMensagem(String mensagem)`: imprime mensagens de status.
- `exibirSaldo(double saldo)`: imprime o saldo final formatado.

### Uso de threads no Módulo 1

O padrão usado é fork-join:

1. **Fork**: criação e início das 5 threads.
2. **Processamento concorrente**: cada thread executa as 1000 vendas.
3. **Join**: a thread principal espera o término de todas.
4. **Consolidação**: leitura e validação do saldo final.

Sem sincronização no model, atualizações poderiam se perder por corrida de dados. Com `synchronized`, o valor permanece consistente.

---

## Módulo 2 - Sem compartilhamento de memória

### Objetivo do módulo

Simular o cálculo do faturamento de 4 filiais independentes. Cada thread recebe apenas os dados da sua filial, calcula localmente e devolve o resultado para consolidação final.

### Arquitetura e classes

#### Main

Inicializa e conecta:

1. `FranquiaView`;
2. `FranquiaService`;
3. `FranquiaController`.

Depois chama o método de cálculo no controller.

#### Controller - `FranquiaController`

Orquestra a execução do caso de uso.

- Define a quantidade de registros por filial (no seu código atual, valor pseudoaleatório na faixa de 9000 a 9999).
- Chama o service para calcular o faturamento total das 4 filiais.
- Recebe o resultado e delega a exibição para a view.

#### Service - `FranquiaService`

É a camada central do processamento paralelo.

1. Cria a coleção de filiais (threads).
2. Para cada filial:
	- gera uma lista local de vendas;
	- instancia um objeto `Filial` com nome e lista;
	- inicia a thread com `start()`.
3. Em seguida, percorre todas as filiais:
	- chama `join()` para esperar o término;
	- soma `getFaturamentoLocal()` ao total geral.
4. Retorna o faturamento total para o controller.

#### Model - `Filial`

Representa uma filial como unidade de processamento.

- Herda de `Thread`.
- Possui lista de vendas própria (`vendas`).
- Possui acumulador local (`faturamentoLocal`).

No método `run()`, percorre a lista da própria filial e calcula sua soma local.

Ponto importante: durante esse cálculo, a thread não depende de variável global compartilhada. Isso implementa o isolamento exigido no enunciado.

#### View - `FranquiaView`

Responsável por exibir mensagens e o faturamento total final no console.

### Uso de threads no Módulo 2

Também segue fork-join, mas com isolamento:

1. **Fork**: 4 threads de filiais são iniciadas.
2. **Processamento local**: cada uma calcula apenas sua própria lista.
3. **Join**: thread principal espera todas terminarem.
4. **Reduce**: soma final dos 4 resultados locais.

Como não há estado global compartilhado entre as threads durante o processamento, o risco de condição de corrida é muito menor. A sincronização explícita não é necessária para a soma local de cada filial.

---

## Comparação didática entre os módulos

### Módulo 1

- Problema de estado compartilhado.
- Exige exclusão mútua (`synchronized`).
- Foco em consistência do mesmo recurso acessado por múltiplas threads.

### Módulo 2

- Problema de processamento independente.
- Cada thread trabalha com dados próprios.
- Foco em paralelismo com isolamento e junção de resultados.

---

## Conclusão

O trabalho demonstra dois cenários clássicos de sistemas concorrentes:

1. **Concorrência com memória compartilhada**: precisa de sincronização para manter integridade dos dados.
2. **Concorrência com isolamento**: divide tarefas independentes e consolida o resultado ao final.

Além disso, a divisão em MVC torna o projeto mais didático:

- o **Controller** controla o fluxo;
- o **Service** processa as regras;
- o **Model** representa estado e dados;
- a **View** cuida da saída.

Essa separação melhora manutenção, leitura do código e organização para futuras expansões.
