package Atividades.Atividade_LogErros;

import java.util.List;

public class Trabalhador implements Runnable {
    
    private List<String> pedacoLog;
    // O índice do array é o tipo do erro (0, 1, 2, 3) e o valor é a quantidade
    private int[] contagemPorTipo; 

    public Trabalhador(List<String> pedacoLog) {
        this.pedacoLog = pedacoLog;
        this.contagemPorTipo = new int[4];
    }

    @Override
    public void run() {
        for (String linha : pedacoLog) {
            String[] colunas = linha.split(",");
            
            if (colunas.length >= 3) {
                int tipoErro = Integer.parseInt(colunas[2]);
                
                // Se o erro for tipo 0, 1, 2 ou 3, soma 1 na posição correspondente
                if (tipoErro >= 0 && tipoErro <= 3) {
                    this.contagemPorTipo[tipoErro]++; 
                }
            }
        }
    }

    public int[] getContagemPorTipo() {
        return this.contagemPorTipo;
    }
}