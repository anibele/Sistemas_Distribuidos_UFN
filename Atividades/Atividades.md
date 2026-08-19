 # Resumo das atividades com threads

Este documento apresenta um resumo dos quatro exercícios desenvolvidos em Java na
pasta `Atividades`. Em todos eles, as threads são utilizadas para dividir o
trabalho ou executar operações simultaneamente. A classe `Thread` representa uma
linha de execução independente, enquanto `Runnable` representa a tarefa que será
executada por uma thread. O método `start()` inicia a execução concorrente e o
método `join()` faz a thread principal aguardar o término de outra thread antes de
continuar.

## Exercício 1 - Divisão e conquista: soma de sublistas

### Objetivo

O objetivo é criar um vetor com 100 números inteiros aleatórios, dividi-lo em
quatro partes iguais e calcular a soma de cada parte em uma thread diferente.
Depois, as quatro somas parciais são reunidas para produzir a soma total do
vetor.

### Como foi feito

1. Foi criado o vetor `listaoriginal` com 100 posições.
2. A classe `Random` foi utilizada para preencher cada posição com um número
	aleatório entre 0 e 99.
3. O tamanho de cada parte foi calculado com `listaoriginal.length / 4`,
	resultando em quatro sublistas de 25 elementos.
4. Foram definidos os índices inicial e final de cada parte. O método
	`Arrays.copyOfRange()` foi usado para criar uma cópia independente de cada
	sublista.
5. Foi criada a classe `SomaThread`, que herda de `Thread`. Cada objeto recebe
	uma sublista no construtor e possui o atributo `soma` para guardar o
	resultado.
6. No método `run()`, a thread percorre sua sublista com um laço `for` e soma
	todos os elementos.
7. As quatro threads são iniciadas com `start()`. Assim, cada uma pode executar
	o cálculo da sua parte de forma concorrente com as demais.
8. A thread principal chama `join()` para as quatro threads. Isso é necessário
	para garantir que todas as somas parciais estejam prontas antes de serem
	lidas pelos métodos `getSoma()`.
9. Por fim, foi criada a classe `SomaTotalThread`. Ela recebe as quatro somas
	parciais e calcula a soma total no método `run()`.
10. A thread principal inicia a thread da soma total, aguarda seu término com
	 `join()` e exibe as sublistas, suas somas e o resultado final.

### Uso das threads

Este exercício aplica o conceito de divisão e conquista. O vetor é dividido em
partes menores, e cada thread resolve uma parte do problema sem alterar as
demais. Como cada thread possui seu próprio objeto `SomaThread` e sua própria
variável `soma`, não há disputa pelo mesmo acumulador durante as somas parciais.

O uso de `join()` estabelece a ordem necessária entre as etapas: primeiro as
quatro somas parciais precisam terminar; somente depois a soma total pode ser
calculada. Embora o programa crie uma nova thread para a soma final, esse cálculo
é uma etapa dependente dos resultados anteriores e não pode começar antes deles.

## Exercício 2 - Filtro de dados independente (Map)

### Objetivo

O exercício lê nomes de usuários a partir do arquivo `nomes.csv`, divide a lista
em dois blocos, limpa os nomes em paralelo e junta os resultados em uma lista
final. As regras de limpeza são remover espaços no início e no fim e converter
os nomes para letras maiúsculas.

### Como foi feito

1. Foi criada uma lista `nomes` do tipo `List<String>`.
2. O arquivo `nomes.csv` é aberto com `BufferedReader` e `FileReader` dentro de
	um `try-with-resources`. Cada linha lida é adicionada à lista.
3. A classe `threadsepara` herda de `Thread` e recebe a lista completa. No seu
	método `run()`, calcula o ponto médio e copia a primeira metade para
	`sublista1` e a segunda metade para `sublista2`.
4. A thread de separação é iniciada e a aplicação chama `join()` para garantir
	que as duas sublistas estejam preenchidas antes de serem utilizadas.
5. A classe `threadsub` também herda de `Thread`. Cada instância recebe uma das
	sublistas e cria sua própria `listaLimpa`.
6. No método `run()`, cada nome é processado por `trim()`, que remove espaços
	em branco no início e no fim, e por `toUpperCase()`, que converte o texto para
	maiúsculas. O resultado é adicionado à lista limpa da própria thread.
7. Duas threads, `threadA` e `threadB`, são iniciadas ao mesmo tempo. Cada uma
	processa um bloco diferente e não modifica a lista da outra.
8. Depois de aguardar as duas threads com `join()`, é criada a classe
	`threadprincipal`. Ela recebe as duas listas limpas, adiciona os elementos
	da primeira e depois os elementos da segunda na `listaFinal`.
9. A thread responsável pela união é iniciada e a aplicação aguarda seu término
	antes de exibir a lista final.

### Uso das threads

O processamento dos nomes é um exemplo de operação Map: cada elemento recebe a
mesma transformação, mas o resultado de um nome não depende do resultado de
outro. Por isso, a lista pode ser dividida em dois blocos e processada em
paralelo. Cada thread escreve apenas na sua própria `listaLimpa`, evitando que
as duas threads disputem a mesma estrutura durante a limpeza.

A separação e a união são etapas de coordenação. A separação precisa terminar
antes do início das threads de limpeza, e a união precisa esperar as duas
limpezas. Os `join()` garantem essas dependências e evitam que uma lista seja
consultada antes de estar pronta.

No código, as sublistas são inicialmente calculadas na thread principal, mas
depois substituídas pelas sublistas produzidas por `threadsepara`. O resultado
funcional é correto; a divisão feita inicialmente no método `main` é apenas
redundante, pois a divisão efetivamente utilizada é a realizada pela thread de
separação.

## Exercício 3 - Leitura e exibição de duas listas

### Objetivo

O objetivo é ler dois arquivos diferentes e armazenar seus conteúdos em listas
apropriadas: `numeros.txt` em uma `List<Integer>` e `nomes.txt` em uma
`List<String>`. Depois, as duas listas devem ser exibidas, também utilizando
threads.

### Como foi feito

1. Foram criadas as listas `numeros` e `nomes` dentro do método `main`.
2. A classe `LerNumeros` implementa `Runnable`, recebe a lista de números e lê
	`numeros.txt` linha a linha. Cada linha é convertida para `Integer` com
	`Integer.parseInt()` e adicionada à lista.
3. A classe `LerNomes` implementa `Runnable`, recebe a lista de nomes e realiza
	o mesmo processo para o arquivo `nomes.txt`, adicionando cada linha como uma
	string.
4. São criadas duas threads, uma com `LerNumeros` e outra com `LerNomes`. As
	duas são iniciadas antes de qualquer `join()`, permitindo que as leituras
	ocorram concorrentemente.
5. A aplicação chama `join()` nas threads de leitura. Assim, garante que os
	arquivos foram completamente processados antes da exibição.
6. A classe `ExibirNumeros` implementa `Runnable` e percorre a lista de números,
	imprimindo cada valor.
7. A classe `ExibirNomes` implementa `Runnable` e percorre a lista de nomes,
	imprimindo cada nome.
8. As duas threads de exibição são iniciadas e, em seguida, a thread principal
	aguarda ambas com `join()`.

### Uso das threads

A leitura é paralelizada porque os arquivos são independentes: a leitura de
`numeros.txt` não precisa esperar a leitura de `nomes.txt`. O mesmo ocorre com a
exibição das listas, que é realizada por duas tarefas separadas.

O primeiro conjunto de `join()` funciona como uma barreira entre as fases. Sem
essa espera, as threads de exibição poderiam começar enquanto as listas ainda
estivessem sendo preenchidas, produzindo resultados incompletos. Depois da
barreira, as listas não sofrem mais alterações e podem ser exibidas.

Como as duas threads de exibição escrevem no mesmo console, a ordem das linhas
pode variar ou ficar intercalada entre números e nomes. Isso é um comportamento
normal da execução concorrente: o sistema operacional define qual thread recebe
tempo de execução a cada momento. O objetivo principal, neste caso, é garantir
que ambas as listas sejam lidas e exibidas usando threads.

## Exercício 4 - Memória compartilhada

### Objetivo

O exercício lê os arquivos `numeros1.txt` e `numeros2.txt` em paralelo e adiciona
todos os valores em uma única lista de inteiros compartilhada. Depois, uma thread
separada exibe os valores reunidos.

### Como foi feito

1. Foi criada uma `ArrayList<Integer>` protegida por
	`Collections.synchronizedList()`. Essa estrutura é referenciada pela
	variável `numeros` e compartilhada pelas threads de leitura.
2. A classe `LerNumeros` implementa `Runnable` e recebe dois parâmetros: a lista
	compartilhada e o nome do arquivo que deve ser lido.
3. O método `run()` abre o arquivo com `BufferedReader`, lê cada linha, remove
	espaços extras com `trim()`, converte o texto para inteiro e adiciona o valor
	à lista compartilhada.
4. São criadas duas instâncias da tarefa: uma para `numeros1.txt` e outra para
	`numeros2.txt`. Cada uma é executada por uma thread diferente.
5. As duas threads começam com `start()` e a thread principal usa `join()` para
	esperar que os dois arquivos sejam totalmente lidos.
6. A classe `ExibirNumeros` recebe a mesma lista compartilhada. Durante a
	iteração, utiliza `synchronized (numeros)` para manter o acesso à lista
	protegido enquanto os valores são impressos.
7. A thread de exibição só é iniciada depois que as duas threads de leitura
	terminam. Após a exibição, a thread principal também aguarda seu término com
	`join()`.

### Uso das threads e da memória compartilhada

Este é o exercício que trabalha explicitamente com memória compartilhada. As
duas threads não possuem listas de saída separadas: ambas adicionam elementos
na mesma lista `numeros`. Como uma `ArrayList` comum não é segura para acesso
concorrente, foi utilizada `Collections.synchronizedList()`, que sincroniza as
operações individuais da lista.

Além disso, a iteração para exibição foi envolvida em um bloco
`synchronized (numeros)`. Essa proteção é importante porque percorrer uma lista
enquanto outra thread ainda pode modificá-la poderia causar inconsistências ou
uma `ConcurrentModificationException`. No programa, os `join()` das threads de
leitura já garantem que nenhuma leitura esteja ocorrendo quando a exibição
começa, mas o bloco sincronizado torna a própria operação de iteração segura.

A ordem final dos números pode variar entre execuções, pois as duas threads
podem terminar e adicionar seus valores em ordens diferentes. O conteúdo da
lista, entretanto, reúne os números dos dois arquivos. O programa prioriza a
execução concorrente das leituras e demonstra como proteger o acesso quando
várias threads compartilham a mesma estrutura de dados.

## Conclusão

Os exercícios mostram diferentes formas de aplicar concorrência em Java:

- no Exercício 1, o trabalho numérico é dividido em quatro partes e os
  resultados são combinados;
- no Exercício 2, duas threads aplicam a mesma transformação a blocos
  independentes de dados;
- no Exercício 3, operações independentes de leitura e exibição são executadas
  em paralelo;
- no Exercício 4, duas threads escrevem em uma estrutura compartilhada, exigindo
  sincronização.

Em todos os casos, `start()` inicia as tarefas e `join()` coordena a passagem
entre etapas dependentes. A principal diferença está no compartilhamento de
dados: quando cada thread trabalha com seus próprios dados, a necessidade de
sincronização é menor; quando várias threads acessam a mesma lista, é necessário
usar estruturas sincronizadas e/ou blocos `synchronized` para preservar a
consistência dos dados.
