Atividade de processamento de logs

Essa atividade foi desenvolvida para simular um sistema distribuído simples de processamento de arquivos de log muito grandes. O objetivo principal era ler um arquivo de log, dividir esse conteúdo em pedaços menores, enviar cada pedaço para um conjunto de trabalhadores em paralelo e depois consolidar os resultados em uma contagem final. A ideia era reproduzir o comportamento de um ambiente em que diferentes threads ou processos trabalham de forma independente, sem compartilhar memória, e devolvem apenas o resumo parcial da sua parte. Ao final, o coordenador organiza esses resumos e apresenta o resultado global.

A implementação foi feita em Java com uso de threads e um pool fixo de execução. O código não usa filas de mensagens reais, mas a lógica segue a mesma ideia: o coordenador cria tarefas, envia cada uma para o pool e espera a conclusão dos workers. Isso permite tratar o processamento como um trabalho paralelo e escalável, sem que cada thread tenha acesso aos dados de outra.

A relação com MVC

Em termos de arquitetura, a estrutura do projeto pode ser relacionada ao padrão MVC da seguinte forma:

- Visao.java representa a View.
- Coordenador.java representa o Controller ou, mais precisamente, o controlador/orquestrador do fluxo da aplicação.
- Trabalhador.java representa a lógica de processamento e a parte de negócio de cada tarefa, com uma relação próxima ao modelo de processamento de dados e também ao papel de um worker no padrão de concorrência.
- Main.java funciona como ponto de entrada da aplicação, inicializando os componentes e disparando a execução.

Essa equivalência ajuda a entender o papel de cada classe no exercício: a interface de saída é a View, o fluxo de execução e coordenação é o Controller e a lógica de processamento dos dados é a parte que transforma cada bloco do log em um resumo parcial.

Classe Main

A classe Main é o ponto de entrada do programa. Ela cria a visão, cria o coordenador e chama o método iniciar, passando o caminho do arquivo erro.log.

O papel dessa classe é simples, mas essencial: ela conecta os elementos do sistema e faz a aplicação começar a rodar. Em uma arquitetura MVC, ela seria o responsável por montar a aplicação e iniciar o fluxo, sem conter a lógica principal de processamento.

Classe Visao

A classe Visao é a responsável por exibir o resultado final para o usuário. Ela contém o método exibirResultadoFinal(int[] totalGeral), que imprime no console quatro valores: a quantidade total de erros dos tipos 0, 1, 2 e 3.

Essa classe funciona como a View do sistema, pois é a parte que apresenta os dados processados ao usuário. No exercício, ela não decide como os dados são calculados; ela apenas recebe o resultado consolidado do coordenador e mostra o valor final. Isso mantém a divisão de responsabilidades, uma ideia central no MVC: a camada de visualização não deve lidar com regras de negócio ou processamento.

Classe Coordenador

A classe Coordenador é o núcleo da coordenação do sistema. Ela recebe uma instância de Visao no construtor e, no método iniciar, realiza os passos principais da atividade:

- cria um pool de threads com ExecutorService newFixedThreadPool(4);
- abre o arquivo de log com BufferedReader;
- lê linha por linha;
- divide o conteúdo em blocos de 50 linhas;
- cria um objeto Trabalhador para cada bloco;
- executa os workers no pool de threads;
- espera o término de todas as tarefas com awaitTermination;
- soma os resultados parciais de todos os trabalhadores em um vetor totalGeral;
- envia esse vetor para a Visao para exibição.

Essa classe funciona como um controlador, pois ela organiza o fluxo do processamento e decide como os dados são distribuídos entre os trabalhadores. Ela também é a parte que une as demais classes: consulta o arquivo, delega o processamento para os workers e, depois, consolida as respostas. Em termos do exercício, ela representa o papel do “coordenador” do cenário descrito no enunciado: ele lê o arquivo grande, divide o trabalho e interpreta o resultado final.

Classe Trabalhador

A classe Trabalhador implementa Runnable e representa cada tarefa individual de processamento de um pedaço do log. Cada instância recebe uma lista de linhas do arquivo e armazena um vetor chamado contagemPorTipo, com quatro posições. Esse vetor serve para guardar a quantidade de erros encontrados por tipo: posição 0 para tipo 0, posição 1 para tipo 1, posição 2 para tipo 2 e posição 3 para tipo 3.

No método run, cada trabalhador percorre as linhas que receberam. A linha é separada por vírgulas com split(","), e o código assume que a terceira coluna indica o tipo do erro. Se essa coluna existir e o valor estiver entre 0 e 3, o trabalhador incrementa o contador correspondente.

Importante: cada Trabalhador processa apenas seu bloco de log, sem modificar variáveis globais compartilhadas. Isso está de acordo com a regra do enunciado: sem memória compartilhada. Em um ambiente real, cada worker seria um processo ou thread isolado, e cada um devolveria um resumo parcial de sua parte. No código, esse resumo é guardado no próprio objeto e depois recuperado pelo coordenador com o método getContagemPorTipo().

Esse comportamento é essencial para o exercício, porque mostra como o processamento paralelo pode ser realizado de forma segura e modular. Cada worker não precisa saber o que aconteceu em outra parte do arquivo; ele apenas localiza e contabiliza os erros do seu pedaço. Assim, o sistema evita condições de corrida e elimina o risco de conflitos de memória.

Como o sistema funciona no conjunto

O fluxo completo da aplicação é o seguinte:

- O programa começa em Main.
- Main cria a Visao e o Coordenador.
- O Coordenador lê o arquivo de log e divide suas linhas em blocos.
- Cada bloco vira uma tarefa para um Trabalhador.
- O pool de threads executa esses trabalhadores em paralelo.
- Cada trabalhador conta apenas os erros do seu pedaço.
- O Coordenador coleta os resultados parciais de cada worker.
- O Coordenador soma todos os valores em um total geral.
- O total geral é enviado para a Visao.
- A Visao imprime o resultado final para o usuário.

Esse fluxo representa bem a ideia do exercício: um sistema distribuído simples, em que a carga é dividida, processada em paralelo e reunida em um resultado final. O programa não usa um broker real, fila de mensagens externa ou rede, mas a lógica da distribuição e da agregação dos dados mantém a mesma estrutura conceitual.

O papel do exercício em sala de aula

Esse exercício tem como objetivo ensinar alguns conceitos fundamentais de sistemas distribuídos e programação concorrente:

- processamento de arquivos grandes sem carregar tudo em memória de uma vez;
- divisão de trabalho em partes menores;
- parallelismo com threads;
- ausência de memória compartilhada para evitar inconsistências;
- agregação de resultados parciais;
- separação de responsabilidades, semelhante ao padrão MVC.

Em outras palavras, o exercício vai além de contar erros em arquivos: ele demonstra como a programação paralela pode organizar tarefas em blocos, como cada unidade de processamento pode agir de forma isolada e como o resultado final pode ser construído a partir de pequenos resumos. Isso é uma boa introdução à forma como sistemas distribuídos e pools de workers funcionam na prática.

Conclusão

O projeto foi estruturado para mostrar, de forma simples e didática, como um arquivo de log grande pode ser processado em paralelo. A classe Visao apresenta o resultado; a classe Coordenador organiza o fluxo e agrega os dados; a classe Trabalhador executa a lógica de análise em cada pedaço; e a classe Main inicia tudo. Essa organização torna o código fácil de entender e mostra diretamente a analogia com o padrão MVC: a visualização da informação, o controle do processamento e a lógica de dados trabalhados de forma independente.
