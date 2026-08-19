# Resumo de Aula: Sistemas Distribuídos

## Sistemas distribuídos - Definição

**Um sistema distribuído é um conjunto de computadores independentes que se comunicam via rede e aparecem para o usuário final como se fossem um único sistema coerente.** 

Em um sistema distribuído, múltiplos nós (máquinas físicas ou virtuais) colaboram para realizar tarefas, processar dados ou fornecer serviços. Eles não compartilham memória física nem um relógio central; toda a coordenação ocorre por meio do envio de mensagens através da rede. O grande objetivo de um sistema distribuído é a transparência: o usuário não precisa saber em qual computador específico sua requisição está sendo processada.

* **Analogia:** Pense em uma equipe de cozinheiros em um restaurante refinado. Cada cozinheiro fica em uma estação diferente e cuida de uma etapa da receita (um faz a massa, outro o molho, outro a sobremesa), comunicando-se por voz. Para o cliente no salão, a comida simplesmente chega pronta como se saísse das mãos de um único "supercozinheiro".
* **Exemplo Real:** O mecanismo de busca do Google, a plataforma da Netflix e os sistemas bancários transacionais. Quando você faz uma busca, milhares de servidores em diferentes datacenters trabalham juntos para entregar o resultado em milissegundos, mas você enxerga apenas uma única tela simples.

---

## O que é grid e como ele é usado em sistemas distribuídos?

**Um grid de computação é uma infraestrutura distribuída que conecta e compartilha recursos heterogêneos de múltiplos computadores geograficamente dispersos para resolver problemas complexos de grande escala.**

Diferente de um cluster local (onde as máquinas costumam ser idênticas e ficar na mesma sala), o grid integra computadores pertencentes a diferentes organizações, com diferentes sistemas operacionais e configurações de hardware. O grid gerencia essa diversidade para criar uma "rede de poder computacional" sob demanda, permitindo rodar simulações massivas sem a necessidade de construir um único supercomputador.

* **Analogia:** A rede elétrica da sua cidade (*power grid*). Você liga a sua geladeira na tomada sem precisar saber se a energia veio de uma usina hidrelétrica, eólica ou solar. O grid computacional faz o mesmo com o processamento: você "conecta" sua tarefa pesada na rede e ela distribui o cálculo entre os computadores disponíveis pelo mundo.
* **Exemplo Real:** O projeto *SETI@home* (que usava o tempo ocioso de computadores pessoais de voluntários para analisar sinais de rádio do espaço) e a rede *Worldwide LHC Computing Grid* da CERN, que processa petabytes de dados de colisões de partículas utilizando mais de 170 centros de computação em 42 países.

---

## Por que os sistemas distribuídos são considerados fracamente acoplados?

**Sistemas distribuídos são considerados fracamente acoplados porque seus componentes operam de forma autônoma e independente, comunicando-se apenas por mensagens sem compartilhar memória ou barramentos físicos.**

O fraco acoplamento (*loose coupling*) significa que os nós da rede possuem independência estrutural e operacional. Se um computador falhar, for desligado ou passar por manutenção, os outros nós continuam operando sem entrar em colapso imediato. A dependência entre os módulos é mínima e intermediada por protocolos de rede padronizados.

* **Analogia:** Uma equipe em regime de *home office*. Cada funcionário trabalha de sua própria casa, usando seu próprio computador. Se a internet da casa de um funcionário cair, os outros continuam trabalhando normalmente, pois a comunicação é feita de forma assíncrona por e-mail ou mensagens.
* **Exemplo Real:** A arquitetura de microserviços de um e-commerce. Se o serviço de recomendações de produtos ficar fora do ar por um problema no servidor, o serviço de carrinho de compras e o checkout de pagamento continuam operando normalmente.

---

## Por que os sistemas distribuídos são considerados sistemas homogêneos?

**Embora a infraestrutura física de um sistema distribuído seja frequentemente heterogênea, ele pode ser considerado homogêneo na camada de software quando utiliza um middleware que unifica e padroniza a interface de operação para todos os nós.**

É fundamental separar a camada física da camada de abstração lógica:
1. **Visão Física (Heterogênea):** As máquinas físicas podem ter diferentes processadores, tamanhos de memória e sistemas operacionais (Linux, Windows, macOS).
2. **Visão Lógica/Middleware (Homogênea):** O software de middleware (como RPC, gRPC, CORBA ou frameworks de nuvem) esconde essas diferenças físicas e oferece uma interface idêntica para o desenvolvedor e o usuário, fazendo o sistema parecer homogêneo.
3. **Casos Específicos:** Em subconjuntos como clusters dedicados, a homogeneidade também é física, onde todas as máquinas possuem exatamente o mesmo hardware e software para maximizar o desempenho.

* **Analogia:** Um time de futebol formado por jogadores de países e idiomas completamente diferentes, mas onde todos usam um fone de ouvido com tradução simultânea e seguem rigorosamente o mesmo esquema tático do treinador. Para quem assiste da arquibancada, o time joga em perfeita sintonia, como se falassem a mesma língua nativa.
* **Exemplo Real:** O ambiente de execução do Kubernetes ou Hadoop. Os servidores físicos podem ser de fabricantes e configurações distintas, mas o ambiente de containers e o sistema de arquivos distribuídos fornecem uma visão homogênea e padronizada para a aplicação.

---

## Como a latência de rede está relacionada com os sistemas distribuídos?

**A latência de rede representa o tempo de atraso para uma mensagem ir de um nó a outro em um sistema distribuído, afetando diretamente a velocidade de resposta, o desempenho e a sincronização do sistema.**

Em computadores isolados, os componentes conversam via barramento de memória em velocidades de nanossegundos. Em sistemas distribuídos, a comunicação depende da rede (cabos de fibra, roteadores, redes sem fio), introduzindo atrasos de milissegundos. Por essa razão, a latência é um dos principais gargalos de projeto, exigindo técnicas como *caching*, comunicação assíncrona e replicação regional de dados para mitigar seus impactos.

* **Analogia:** Enviar uma dúvida para um colega por carta pelos Correios versus falar diretamente com ele na mesma mesa. Se você precisa da resposta da carta para continuar seu trabalho (operação síncrona bloqueante), você perde um enorme tempo esperando o mensageiro ir e voltar.
* **Exemplo Real:** Jogos multiplayer online. Se o servidor do jogo fica em outro continente e você está no Brasil, a latência de rede (ping) alta faz com que suas ações no controle demorem alguns milissegundos a mais para acontecer no jogo (o famoso *lag*).

---

## Como o TCP/IP está relacionado com sistemas distribuídos?

**O protocolo TCP/IP é o padrão de comunicação fundamental da Internet que permite que computadores heterogêneos troquem dados de forma confiável e estruturada em um sistema distribuído.**

O conjunto TCP/IP divide o trabalho de rede em camadas:
* **IP (Internet Protocol):** Responsável por endereçar e rotear os pacotes de dados pela rede, garantindo que cheguem ao destino correto.
* **TCP (Transmission Control Protocol):** Fornece um canal de comunicação orientado à conexão e confiável, garantindo que os dados sejam entregues na ordem correta, sem perdas, sem duplicatas e realizando retransmissões automáticas em caso de falhas.
Sistemas distribuídos utilizam a abstração do TCP/IP para garantir que mensagens de controle e dados trafeguem com integridade entre nós distantes.

* **Analogia:** O TCP/IP funciona como o serviço de entrega expressa de uma transportadora com código de rastreamento. O endereço de entrega impresso na caixa é o IP, enquanto o protocolo de conferência que garante que todas as caixas da sua mudança chegaram sem quebrar e na ordem certa é o TCP.
* **Exemplo Real:** Quando um aplicativo de banco no seu celular realiza uma transferência Pix, a requisição trafega sobre conexões TCP/IP criptografadas (HTTPS/TLS) garantindo que nenhum dado financeiro se perca pelo caminho.

---

## Diferença entre sistemas distribuídos e sistemas paralelos

**Sistemas paralelos utilizam múltiplos processadores com memória compartilhada ou comunicação de alta velocidade em uma única máquina/cluster coeso para executar tarefas simultaneamente, enquanto sistemas distribuídos conectam computadores autônomos sem memória compartilhada através de redes.**

### Pontos exclusivos de sistemas paralelos:
* **Cluster:** Conjunto de computadores de alto desempenho interconectados por redes locais dedicadas de baixíssima latência (ex: InfiniBand), operando como se fossem uma única supermáquina concentrada.
* **Fortemente Acoplados:** Os processadores compartilham memória central ou barramentos de altíssima velocidade, dependendo diretamente do estado de execução uns dos outros em tempo real.
* **Sistemas Homogêneos:** As máquinas e componentes do cluster possuem hardware, arquitetura de CPU e sistemas operacionais rigorosamente idênticos.
* **GPUs e Multicores:** Utilização de múltiplos núcleos de processamento no mesmo chip ou centenas de núcleos em placas gráficas para executar o mesmo cálculo sobre grandes volumes de dados (arquitetura SIMD/MIMD).
* **N processos executando ao mesmo tempo:** Simultaneidade física real, onde N instruções ou processos são processados rigorosamente no exato mesmo nanossegundo pelos diferentes núcleos da CPU/GPU.

* **Analogia:**
  * **Sistema Paralelo:** Uma fábrica de automóveis moderna com 10 robôs dentro do mesmo galpão, conectados à mesma esteira elétrica e ao mesmo computador central, montando o mesmo carro ao mesmo tempo.
  * **Sistema Distribuído:** Uma montadora de veículos com fábricas em países diferentes: a fábrica da Alemanha faz os motores, a do Brasil faz o chassis e a do México faz a lataria, trocando peças e informações por navios e sistemas de rede.

---

## Programação Concomitante x Programação Concorrente, qual a diferença?

**Programação concorrente é a técnica de estruturar um programa em múltiplas tarefas independentes que podem ser intercaladas no tempo, enquanto o termo concomitante (ou simultâneo/paralelo) refere-se à execução física exatamente no mesmo instante em múltiplos núcleos de processamento.**

* **Concorrência:** É um conceito de *design/estrutura* de software. Significa gerenciar o progresso de várias tarefas ao mesmo tempo. Em uma CPU de núcleo único, a concorrência é obtida alternando rapidamente a execução das tarefas (*troca de contexto*), dando a ilusão de simultaneidade.
* **Concomitância / Paralelismo:** É um conceito de *hardware/execução*. Ocorre quando duas ou mais tarefas estão rodando fisicamente no exato mesmo instante de tempo em núcleos de processamento distintos.

* **Analogia:**
  * **Concorrência:** Um malabarista equilibrando 3 bolinhas no ar com uma única mão. Ele só toca em uma bolinha por vez, mas alterna tão rápido que todas parecem estar no ar ao mesmo tempo.
  * **Concomitância:** Três malabaristas, cada um jogando e pegando sua própria bolinha ao mesmo tempo com suas próprias mãos.

---

## O que são recursos compartilhados em sistemas?

**Recursos compartilhados são elementos de hardware ou software em um sistema que podem ser acessados e utilizados por múltiplos processos, threads ou usuários simultaneamente ou de forma alternada.**

Os recursos compartilhados dividem-se em:
* **Recursos Físicos (Hardware):** Impressoras, discos rígidos, placas de rede, memória RAM e núcleos de CPU.
* **Recursos Lógicos (Software):** Arquivos de texto, tabelas de banco de dados, variáveis na memória, filas de mensagens e sockets de conexão.
O acesso a esses recursos exige mecanismos de controle e sincronização para evitar conflitos, corrupção de dados e disputas desordenadas.

* **Analogia:** Uma impressora única no centro de um escritório corporativo. Vários funcionários (processos) enviam documentos para impressão. A impressora é o recurso compartilhado e precisa de uma fila organizada para não misturar as páginas dos documentos de pessoas diferentes.
* **Exemplo Real:** Uma variável `saldo` em um sistema bancário, acessada simultaneamente por um saque no caixa eletrônico e por um pagamento de boleto no aplicativo de celular.

---

## Para que usar sistemas distribuídos?

**A finalidade principal de usar sistemas distribuídos é compartilhar recursos, além de proporcionar alta disponibilidade, tolerância a falhas e escalabilidade para aplicações computacionais.**

Resumindo: **para compartilhar recursos** (poder computacional, dados, armazenamento e serviços).

Principais vantagens do uso de sistemas distribuídos:
1. **Compartilhamento de Recursos:** Permitir que múltiplos usuários acessem bases de dados e periféricos caros de forma remota.
2. **Escalabilidade Horizontal (*Scale-out*):** Aumentar a capacidade do sistema adicionando computadores comuns em vez de comprar um único supercomputador extremamente caro (*Scale-up*).
3. **Tolerância a Falhas e Disponibilidade:** Se um servidor falhar, outros nós da rede assumem a carga de trabalho imediatamente sem derrubar o sistema.
4. **Desempenho e Distribuição Geográfica:** Colocar servidores mais próximos dos usuários finais em diferentes regiões do planeta para diminuir a latência.

* **Analogia:** Em vez de contratar um único "super-humano" caríssimo para tentar realizar o trabalho de 100 pessoas sozinho, você contrata 100 pessoas normais e as organiza em uma equipe bem comunicada por telefone para dividirem o trabalho.

---

## Como sistemas distribuídos operam?

**Sistemas distribuídos operam coordenando múltiplos nós autônomos que trocam mensagens padronizadas através da rede para executar tarefas, sincronizar estados e apresentar uma interface unificada ao usuário.**

A operação ocorre em arquitetura em camadas:
1. **Infraestrutura e Rede:** A fiação física e o protocolo TCP/IP encarregados do transporte de dados.
2. **Sistemas Operacionais Locais:** Gerenciam os recursos de hardware de cada nó individual.
3. **Middleware:** A camada de software que fica sobre o sistema operacional e abstrai a complexidade da rede, gerenciando chamadas de rotinas remotas (RPC/RMI), autenticação e serialização.
4. **Aplicação Distribuída:** A lógica de negócio que interage com o middleware utilizando modelos como Cliente-Servidor ou Ponto-a-Ponto (P2P).

* **Analogia:** Uma orquestra sinfônica sem um maestro visível para a plateia. Cada músico (nó) lê sua própria partitura, escuta o tempo dos colegas ao lado e ajusta o ritmo para que a música saia perfeita e unificada para o público.

---

## Como ocorre a comunicação em sistemas distribuídos? Como os dados estão relacionados ao TCP/IP?

**A comunicação em sistemas distribuídos ocorre por meio da troca de mensagens enviadas entre nós da rede, onde os dados da aplicação são convertidos em sequências de bytes e empacotados pelo protocolo TCP/IP para trafegarem com segurança e confiabilidade.**

Como a memória física não é compartilhada, a aplicação distribuída precisa:
1. **Serializar:** Converter objetos e estruturas de dados complexas em um fluxo linear de bytes brutos (*marshalling*).
2. **Transmitir via TCP/IP:** Entregar esse fluxo de bytes à camada de transporte do TCP/IP. O TCP adiciona cabeçalhos de controle (garantindo ordenação e integridade) e o IP divide os dados em pacotes de rede com endereços de origem e destino.
3. **Desserializar:** No nó receptor, a pilha TCP/IP entrega os bytes à aplicação, que os reconverte no objeto/dado original (*unmarshalling*).

* **Analogia:** Desmontar um guarda-roupa em peças menores e colocar cada peça em uma caixa numerada (serialização), enviar as caixas pelo correio com o endereço do seu amigo (TCP/IP) e o seu amigo abrir as caixas na casa dele e remontar o guarda-roupa exatamente igual (desserialização).

---

## O que é um socket e o que são datagramas serializados?

**Um socket é uma abstração de software que funciona como um ponto final de comunicação (combinação de IP e porta) para envio e recebimento de dados na rede, enquanto datagramas serializados são pacotes de dados convertidos em fluxo de bytes para serem transmitidos por esse canal.**

* **Socket:** É a interface de programação (API) fornecida pelo sistema operacional que funciona como uma "porta de comunicação". Um socket é identificado de forma única pela combinação: `Endereço IP + Porta` (exemplo: `192.168.1.15:8080`).
* **Datagramas Serializados:** Um datagrama é uma unidade autônoma de dados transmitida na rede (comum no protocolo UDP). Quando dizemos "datagrama serializado", trata-se de uma estrutura de dados de software que foi traduzida para uma sequência contínua de bytes para ser transportada dentro do *payload* (carga útil) do pacote de rede.

* **Analogia:** O socket é a tomada elétrica ou a caixa de correio na parede da sua casa. O datagrama serializado é a carta padronizada e dobrada em formato específico para caber exatamente dentro do envelope que passa pela fresta da caixa de correio.

---

## Categorias de comunicação:

**As categorias de comunicação em redes definem o número de destinatários para os quais uma mensagem é enviada a partir de uma origem.**

### Broadcast
**Broadcast é a modalidade de comunicação onde uma mensagem é enviada de um único remetente para absolutamente todos os nós presentes em uma mesma sub-rede.**
Não há seleção de público: todos os dispositivos conectados naquela rede recebem e processam o pacote no nível de placa de rede.

* **Analogia:** O alto-falante do sistema de som de uma escola ou shopping anunciando um comunicado para todas as pessoas que estão no local.

### Multicast
**Multicast é a modalidade de comunicação onde uma mensagem é enviada de um remetente para um grupo específico de nós interessados na rede.**
Apenas os nós que se inscreveram no grupo multicast recebem a transmissão, otimizando o uso da largura de banda da rede.

* **Analogia:** Uma mensagem enviada dentro de um grupo temático do WhatsApp. Apenas os contatos que fazem parte daquele grupo recebem a notificação.

### Unicast
**Unicast é a modalidade de comunicação direta de ponto a ponto, enviada de um único remetente para um único destinatário específico.**
É a forma mais comum de comunicação na Internet para acesso a sites e serviços.

* **Analogia:** Uma ligação telefônica privada ou uma conversa direta via mensagem individual entre você e um amigo.

---

## Definição de alguns termos:

### Reader / Sender
**Sender é a entidade que gera e envia mensagens para a rede, enquanto Reader é o componente encarregado de escutar e ler o fluxo de dados do canal de entrada.**
* **Sender (Remetente):** Pega os dados da aplicação, empacota e envia através de um socket de saída.
* **Reader (Leitor):** Escuta a conexão de entrada, lê os bytes armazenados no *buffer* do sistema operacional e os repassa para o processamento do programa.

### Writer / Receiver
**Writer é a entidade que grava os bytes da mensagem no fluxo de saída do socket, enquanto Receiver é o nó/processo destino que aceita a conexão e recebe os dados enviados.**
* **Writer (Escritor):** Formata e escreve o fluxo de dados na interface de comunicação da aplicação.
* **Receiver (Receptor):** É a ponta de destino que captura os pacotes recebidos pela rede e confirma o recebimento.

* **Analogia:** O *Sender/Writer* é a pessoa que escreve a carta e a coloca na caixa do correio. O *Receiver/Reader* é a pessoa que retira a carta da caixa de correio do destino e lê o texto escrito.

---

## O que são Threads?

**Uma thread (ou linha de execução) é a menor unidade de código instrucional que pode ser gerenciada e agendada de forma independente pelo sistema operacional dentro de um processo.**

Diferente de um processo (que possui seu próprio espaço de memória isolado), múltiplas threads criadas dentro do mesmo processo compartilham a mesma memória (código, variáveis globais e *heap*), mantendo individualmente apenas seus registradores e sua pilha de execução (*stack*). Isso torna a criação e a troca de contexto entre threads extremamente leve e rápida em comparação à troca entre processos inteiros.

* **Analogia:** Um processo é como uma cozinha inteira de um restaurante. As threads são os diferentes cozinheiros trabalhando simultaneamente dentro dessa mesma cozinha, compartilhando as bancadas, panelas e ingredientes.
* **Exemplo Real:** Em um editor de texto, uma thread cuida da digitação do usuário no teclado, outra thread faz a checagem ortográfica em segundo plano e uma terceira thread faz o salvamento automático do arquivo no disco.

---

## Porque sistemas distribuídos usam threads?

**Sistemas distribuídos usam threads para atender múltiplas conexões de rede de forma simultânea e não bloqueante, evitando que o tempo de espera por respostas de E/S trave o servidor.**

Operações de rede possuem alta latência se comparadas ao processamento interno de CPU. Se um servidor distribuído utilizasse apenas uma thread principal, ele ficaria completamente travado (*bloqueado*) enquanto esperava a chegada de dados de um cliente pela rede, impedindo todos os outros clientes de se conectarem. Ao usar threads, o servidor pode atribuir uma nova thread para cada cliente conectado, permitindo processar milhares de requisições concorrentes.

* **Analogia:** Os guichês de atendimento de um banco. Se houvesse apenas um atendente (uma thread), todos os clientes formariam uma única fila imensa e o banco pararia a cada cliente demorado. Com vários atendentes (múltiplas threads), vários clientes são atendidos ao mesmo tempo.

---

## O que é um processo bloqueante?

**Um processo bloqueante é aquele que suspende sua execução e entra em estado de espera até que uma operação de Entrada/Saída (E/S) ou um evento externo seja concluído.**

Quando um programa faz uma chamada síncrona para ler dados de um socket de rede ou do disco rígido, a CPU interrompe a execução das instruções desse programa e altera seu estado para *BLOCKED*. O processo só retorna ao estado de pronto (*READY*) quando os dados chegam da rede.
* **Relação com Threads e E/S:** Para gerenciar a ordem das operações de E/S sem congelar o sistema, utiliza-se threads: a thread encarregada da leitura da rede bloqueia aguardando os dados, enquanto as demais threads da aplicação continuam executando outras tarefas normalmente.

* **Analogia:** Fazer um pedido no balcão de um restaurante e ter que ficar parado na frente do caixa sem poder fazer nada até o prato ficar pronto, versus pegar um senha e sentar na mesa para conversar enquanto espera o garçom chamar seu número.

---

## O que é a arquitetura cliente servidor?

**A arquitetura cliente-servidor é um modelo de aplicação distribuída que divide as tarefas entre os provedores de um serviço (servidores) e os solicitantes desse serviço (clientes).**

Neste modelo, a comunicação é sempre iniciada pelo cliente através de uma requisição (*request*). O servidor, que permanece em modo de escuta contínua, recebe o pedido, processa a regra de negócio e retorna uma resposta (*response*).

### Serverside
**Server-side refere-se a todo o código, processamento e lógica de negócios que são executados diretamente no servidor.**
Trata operações pesadas, regras de segurança, autenticação de usuários e acesso a bancos de dados sigilosos.

### Clientside
**Client-side refere-se ao ambiente e código executados localmente no dispositivo do usuário final.**
Inclui a interface gráfica (telas, botões), captura de entradas do usuário e validações simples de formulário.

### Serversocket
**ServerSocket é um socket especial localizado no servidor que escuta uma porta de rede específica aguardando tentativas de conexão de clientes.**
Quando um cliente tenta se conectar, o `ServerSocket` aceita a conexão (`accept()`) e cria um socket regular para tratar a comunicação com aquele cliente.

### Clientsocket
**ClientSocket é o socket instanciado no cliente que inicia ativamente a conexão informando o endereço IP e a porta do servidor de destino.**

* **Analogia Geral:** Um restaurante tradicional:
  * **Client-side:** O salão e o cardápio que o cliente visualiza na mesa.
  * **ClientSocket:** O garçom que anota o pedido da mesa.
  * **ServerSocket:** A recepção da cozinha que recebe a ficha do pedido trazida pelo garçom.
  * **Server-side:** Os cozinheiros preparando a refeição na cozinha protegida de olhares externos.

---

## As threads encapsulam a informação?

**Não, as threads não encapsulam totalmente a informação em nível de memória, pois compartilham o mesmo espaço de endereçamento global do processo onde residem.**

Embora cada thread possua seu próprio espaço privado de pilha (*stack*) para variáveis locais de métodos, todos os dados alocados no *heap* (como objetos, atributos de classe, variáveis estáticas e globais) são compartilhados livremente por todas as threads do mesmo processo. Diferente do encapsulamento da Orientação a Objetos ou do isolamento rígido entre processos distintos, as threads não oferecem proteção de memória interna contra acessos simultâneos indevidos.

* **Analogia:** Pessoas trabalhando dentro da mesma sala. Cada pessoa tem seu próprio bloco de notas no bolso (a pilha privada da thread), mas todas escrevem e leem no mesmo quadro branco na parede da sala (a memória compartilhada do processo).

---

## O que é wrapping?

**Wrapping (ou empacotamento) é a técnica de envolver um dado, objeto ou estrutura de código dentro de outra camada para ocultar complexidades, adaptar interfaces ou adicionar funcionalidades adicionais.**

No contexto de sistemas distribuídos e redes:
1. **Encapsulamento em Redes:** Envolver os dados de uma camada dentro do cabeçalho da camada inferior (ex: dados HTTP envolvidos em um segmento TCP, envolvido em um pacote IP, envolvido em um quadro Ethernet).
2. **Wrapper Classes/Objects em Programação:** Envolver tipos primitivos em objetos (ex: transformar `int` em `Integer` em Java) ou criar classes que envolvem chamadas complexas de sockets em métodos simples como `cliente.enviarTexto("Olá")`.

* **Analogia:** Embalar um presente. O produto original é colocado em uma caixa, envolvido em papel de presente com laço e etiquetado com o endereço de entrega para poder trafegar com segurança pela transportadora.

---

## O que podemos entender por rotinas, tarefas e instruções?

**Instruções, rotinas e tarefas representam diferentes níveis de abstração na execução de um software, variando do nível de hardware até o nível conceitual da aplicação.**

* **Instruções:** São os comandos mais básicos em linguagem de máquina (ex: `ADD`, `MOV`, `JMP`) que a CPU executa diretamente em ciclo de *clock*.
* **Rotinas (Sub-rotinas / Funções):** São blocos organizados de código e instruções reusáveis desenvolvidos para realizar um cálculo ou ação específica (ex: uma função `calcularDesconto()`).
* **Tarefas (Tasks):** São unidades de trabalho de alto nível gerenciadas pelo sistema operacional ou pela aplicação (podendo abranger processos ou threads) que combinam várias rotinas para atingir um objetivo completo (ex: "gerar relatório fiscal em PDF").

* **Analogia:**
  * **Instrução:** O movimento mecânico do braço de ligar a chave do fogão.
  * **Rotina:** A receita estruturada de "como refogar o tempero".
  * **Tarefa:** O objetivo completo do jantar: "fazer o almoço de domingo".

---

## O que é execução concorrente?

**Execução concorrente é a capacidade do sistema de gerenciar e fazer progredir múltiplas tarefas em períodos de tempo sobrepostos, alternando o uso do processador ou executando em paralelo.**

Na execução concorrente, o escalonador do sistema operacional pode chavear o processador entre diferentes tarefas (*troca de contexto*) tão rapidamente em um único núcleo que elas parecem estar rodando ao mesmo tempo. A marca registrada da concorrência é que o início da Tarefa B ocorre antes da finalização da Tarefa A.

* **Analogia:** Um chef de cozinha preparando um prato sozinho. Ele coloca a água do macarrão para ferver e, enquanto a água esquenta, começa a picar os tomates para o molho. Ele faz as duas tarefas progredirem na mesma janela de tempo, embora seja uma pessoa só.

---

## O que é seção crítica?

**Seção crítica é qualquer trecho de código em um programa concorrente que acessa um recurso compartilhado e que não pode ser executado por mais de uma thread ou processo simultaneamente para evitar a corrupção de dados.**

Se duas ou mais threads entrarem na seção crítica ao mesmo tempo, ocorre uma **condição de corrida** (*race condition*), na qual o resultado final do programa torna-se imprevisível e incorreto. Para proteger a seção crítica, utilizam-se técnicas de **exclusão mútua** (como Mutexes, Semáforos e blocos `synchronized`).

* **Analogia:** O banheiro individual de uma cabine de avião. Apenas um passageiro (thread) pode entrar no banheiro (seção crítica) de cada vez. A trava na porta sinaliza "Ocupado" para impedir que outra pessoa entre ao mesmo tempo.

---

## O que é memória compartilhada?

**Memória compartilhada é um espaço de endereçamento de memória RAM acessível simultaneamente por múltiplos processos ou threads para realizar troca rápida de informações.**

Em um único computador ou em sistemas paralelos fortemente acoplados, a memória compartilhada é a forma mais rápida de Comunicação Interprocessos (IPC), pois dispensa a necessidade de copiar dados e trafegá-los via rede. Por outro lado, exige o uso rigoroso de mecanismos de sincronização para gerenciar o acesso concorrente às seções críticas.

* **Analogia:** Uma lousa comunitária fixada no meio de um escritório. Qualquer membro da equipe pode ler o que está escrito ou escrever novas informações para que todos vejam na hora.

---

## O que é sincronismo?

**Sincronismo é a coordenação temporal da execução de múltiplos processos ou threads para garantir a ordem correta das operações e a integridade do estado compartilhado.**

O sincronismo atua em duas frentes principais:
1. **Sincronização de Acesso:** Garantir exclusão mútua em seções críticas para evitar acessos simultâneos conflitantes.
2. **Sincronização de Comunicação:** Garantir a ordenação correta das tarefas (ex: a thread de leitura deve esperar a thread de gravação terminar de gerar o arquivo antes de tentar lê-lo).

* **Analogia:** O semáforo de trânsito em um cruzamento movimentado. Ele coordena o tempo de passagem dos carros das diferentes vias para garantir que eles passem em ordem sem colidirem no centro do cruzamento.

---

## O que é um semáforo no contexto de sincronia?

**Um semáforo é uma variável inteira especial e protegida utilizada para sinalizar e controlar o acesso concorrente de múltiplas threads ou processos a recursos compartilhados limitados.**

Criado por Edsger Dijkstra, o semáforo opera com duas operações atômicas fundamentais:
* `wait()` / `P()` / `acquire()`: Decrementa o valor do semáforo. Se o contador for menor ou igual a zero, a thread requisitante fica bloqueada aguardando liberação.
* `signal()` / `V()` / `release()`: Incrementa o valor do semáforo. Se houver threads bloqueadas, libera uma delas para continuar.
* **Semáforo Binário (Mutex):** Possui valor 0 ou 1 (usado para exclusão mútua simples).
* **Semáforo Contador:** Possui valor N (permite até N acessos simultâneos ao recurso).

* **Analogia:** Um painel eletrônico na entrada do estacionamento de um shopping indicando o número de vagas disponíveis (ex: 5 vagas). Cada carro que entra reduz o painel em 1. Quando o painel chega a 0, a cancela não abre e os novos carros ficam aguardando na fila até que algum veículo saia e libere uma vaga.

---

## Por que podemos dizer que quando não temos memória compartilhada não temos seção crítica?

**Podemos dizer que não temos seção crítica de memória quando não há memória compartilhada porque cada nó possui seu próprio espaço de endereçamento totalmente isolado, impedindo que threads de processos distintos sobrescrevam diretamente as mesmas variáveis físicas.**

O problema clássico da seção crítica ocorre quando múltiplas threads tentam ler e escrever no mesmo endereço de memória física ao mesmo tempo. Em um sistema distribuído baseado puramente na troca de mensagens (sem memória compartilhada), não existe ponteiro de memória em comum que possa ser corrompido diretamente por outro nó.
* **Nota importante:** Embora não exista seção crítica de *memória física local*, em nível lógico do sistema distribuído pode existir disputa por *recursos compartilhados remotos* (como um banco de dados centralizado ou um arquivo em rede), exigindo algoritmos de exclusão mútua distribuída.

* **Analogia:** Se você e seu amigo estão em casas separadas escrevendo em seus próprios cadernos de anotações privados, é fisicamente impossível um rabiscar a página do outro ao mesmo tempo. A disputa física pela página só acontece se ambos tentarem escrever na mesma folha de papel em cima da mesma mesa.

---

## Qual a diferença de extender a classe Thread ou usar a interface Runnable em Java?

**A diferença principal em Java é que extender a classe `Thread` limita a estrutura do código devido à herança simples da linguagem, enquanto implementar a interface `Runnable` desacopla a tarefa da execução e mantém a flexibilidade de herança.**

* **`extends Thread`:** Sua classe passa a herdar diretamente de `Thread`. Como Java não permite herança múltipla, sua classe fica impedida de estender qualquer outra classe base. Além disso, mescla a definição do trabalho com o mecanismo de execução da thread.
* **`implements Runnable`:** Sua classe define apenas a tarefa a ser executada no método `run()`. A tarefa é repassada para um objeto `Thread` separado (`new Thread(runnable)`). Isso respeita os princípios de Orientação a Objetos (separação de responsabilidades), permite estender outra classe e facilita o uso com pools de threads (`ExecutorService`).

* **Analogia:**
  * **Extender Thread:** Você se fantasia e se torna o motorista de táxi. Você fica preso a essa única identidade e não pode assumir outro papel no sistema.
  * **Implementar Runnable:** Você escreve o itinerário da viagem em um papel (a tarefa) e entrega o papel para qualquer motorista profissional contratado realizar o percurso.

---

## Qual a diferença de usar um objeto Thread nomeado ou uma lista de objetos Thread sem nome?

**A diferença principal é a capacidade de identificação e controle individualizado: uma thread nomeada pode ser monitorada e manipulada diretamente pelo seu nome no código e nos logs, enquanto uma lista de threads sem nome trata as threads como um grupo genérico gerenciado em lote.**

* **Thread Nomeada (`Thread t = new Thread(runnable, "Thread-Processa-Pix")`):** Permite referência direta para controle individual (`t.join()`, `t.interrupt()`) e é fundamental para depuração (*debugging*), pois o nome customizado aparece claramente nos rastros de erro (*stack traces*) e ferramentas de monitoramento de desempenho.
* **Lista de Threads sem Nome (`List<Thread> lista = new ArrayList<>()`):** É indicada para gerenciamento dinâmico em lote, onde o número de threads varia em tempo de execução. Permite aguardar a conclusão de todas com um laço de repetição (`for (Thread t : lista) t.join();`), mas torna difícil identificar individualmente qual thread específica apresentou falha caso ocorra uma exceção.

* **Analogia:**
  * **Thread Nomeada:** Chamar os membros de uma equipe pelos seus nomes próprios ("Ana", "Carlos"). Você sabe exatamente quem está fazendo qual tarefa e pode falar diretamente com cada um.
  * **Lista sem Nome:** Tratar os trabalhadores apenas por números na fila de crachás ("Trabalhador 1", "Trabalhador 2"). É prático para mandar o grupo inteiro avançar junto, mas difícil de saber exatamente quem cometeu um erro sem parar para checar um por um.