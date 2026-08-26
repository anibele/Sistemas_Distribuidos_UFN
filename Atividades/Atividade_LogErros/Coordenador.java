package Atividades.Atividade_LogErros;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Coordenador {
    
    private Visao visao;

    public Coordenador(Visao visao) {
        this.visao = visao;
    }

    public void iniciar(String arquivoLocal) throws Exception {
        ExecutorService pool = Executors.newFixedThreadPool(4);
        List<Trabalhador> trabalhadores = new ArrayList<>();
        
        BufferedReader leitor = new BufferedReader(new FileReader(arquivoLocal));
        String linha;
        List<String> pedaco = new ArrayList<>();

        // DIVIDE O ARQUIVO EM PEDAÇOS DE 50 LINHAS
        while ((linha = leitor.readLine()) != null) {
            pedaco.add(linha);
            
            if (pedaco.size() == 50) {
                Trabalhador t = new Trabalhador(pedaco);
                trabalhadores.add(t);
                pool.execute(t);
                
                pedaco = new ArrayList<>();
            }
        }

        if (!pedaco.isEmpty()) {
            Trabalhador t = new Trabalhador(pedaco);
            trabalhadores.add(t);
            pool.execute(t);
        }
        leitor.close();


        pool.shutdown();
        pool.awaitTermination(1, TimeUnit.HOURS);

        // VARIÁVEL FINAL QUE GUARDA A SOMA DE TUDO (Junção dos resultados)
        int[] totalGeral = new int[4]; 
        
        for (Trabalhador t : trabalhadores) {
            int[] resumoDaThread = t.getContagemPorTipo();
            
            totalGeral[0] += resumoDaThread[0];
            totalGeral[1] += resumoDaThread[1];
            totalGeral[2] += resumoDaThread[2];
            totalGeral[3] += resumoDaThread[3];
        }

        visao.exibirResultadoFinal(totalGeral);
    }
}