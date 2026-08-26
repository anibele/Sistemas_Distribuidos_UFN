package Atividades.Atividade_LogErros;
public class Visao {
    
    public void exibirResultadoFinal(int[] totalGeral) {
        System.out.println("Processamento concluído!");
        System.out.println("Total de erros do tipo 0: " + totalGeral[0]);
        System.out.println("Total de erros do tipo 1: " + totalGeral[1]);
        System.out.println("Total de erros do tipo 2: " + totalGeral[2]);
        System.out.println("Total de erros do tipo 3: " + totalGeral[3]);
    }
}