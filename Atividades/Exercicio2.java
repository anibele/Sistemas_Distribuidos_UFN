/*
Filtro de Dados Independente (Map)
Contexto: Limpeza e saneamento de bases de dados.
Problema: Você tem uma lista com 50 strings contendo nomes de usuários gravados em um arquivo txt.
Ação: Divida a lista em 2 blocos. A Thread A recebe a primeira metade e a Thread B recebe a segunda metade. 
Cada thread deve processar sua sublista isolada, aplicando regras de limpeza: remover espaços em branco no início/fim 
e converter todo o texto para letras maiúsculas.
Encerramento: Cada thread retorna uma nova lista limpa. A thread principal junta as duas listas resultantes.
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

//criando a thread que separa em duas sublistas
class threadsepara extends Thread {
    private List<String> lista;
    private List<String> sublista1;
    private List<String> sublista2;

    public threadsepara(List<String> lista) {
        this.lista = lista;
        this.sublista1 = new ArrayList<>();
        this.sublista2 = new ArrayList<>();
    }

    public List<String> getSublista1() {
        return sublista1;
    }

    public List<String> getSublista2() {
        return sublista2;
    }

    @Override
    public void run() {
        int meio = lista.size() / 2;
        sublista1.addAll(lista.subList(0, meio));
        sublista2.addAll(lista.subList(meio, lista.size()));
    }
}

//criando a thread generica para processar a sublista
class threadsub extends Thread {
    private List<String> sublista;
    private List<String> listaLimpa;

    public threadsub(List<String> sublista) {
        this.sublista = sublista;
        this.listaLimpa = new ArrayList<>();
    }

    public List<String> getListaLimpa() {
        return listaLimpa;
    }

    @Override
    public void run() {
        for (String nome : sublista) {
            String nomeLimpo = nome.trim().toUpperCase(); //trim() remove espaços em branco no início/fim
            listaLimpa.add(nomeLimpo);
        }
    }
}

//criando a thread principal que juntar as duas listas resultantes
class threadprincipal extends Thread {
    private List<String> lista1;
    private List<String> lista2;
    private List<String> listaFinal;

    public threadprincipal(List<String> lista1, List<String> lista2) {
        this.lista1 = lista1;
        this.lista2 = lista2;
        this.listaFinal = new ArrayList<>();
    }

    public List<String> getListaFinal() {
        return listaFinal;
    }

    @Override
    public void run() {
        listaFinal.addAll(lista1);
        listaFinal.addAll(lista2);
    }
}

public class Exercicio2 {
    public static void main(String[] args) {
        List<String> nomes = new ArrayList<>();
        System.out.println("Lendo nomes do arquivo...");
        try (BufferedReader br = new BufferedReader(new FileReader("nomes.csv"))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                nomes.add(linha);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Dividindo a lista em duas partes
        int meio = nomes.size() / 2;
        System.out.println("Lista original: " + nomes);
        List<String> sublista1 = nomes.subList(0, meio);
        List<String> sublista2 = nomes.subList(meio, nomes.size());


        // Criando e iniciando as threads
        threadsepara threadSeparar = new threadsepara(nomes);
        threadSeparar.start();

        // Esperando a thread separar terminar
        try {
            threadSeparar.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sublista1 = threadSeparar.getSublista1();
        sublista2 = threadSeparar.getSublista2();

        //mostrando as sublistas
        System.out.println("\nSublista 1: " + sublista1);
        System.out.println("\nSublista 2: " + sublista2);

        // Criando e iniciando as threads
        threadsub threadA = new threadsub(sublista1);
        threadsub threadB = new threadsub(sublista2);
        threadA.start();
        threadB.start();

        // Esperando as threads terminarem
        try {
            threadA.join();
            threadB.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Criando e iniciando a thread principal para juntar as listas
        threadprincipal threadPrincipal = new threadprincipal(threadA.getListaLimpa(), threadB.getListaLimpa());
        threadPrincipal.start();

        // Esperando a thread principal terminar
        try {
            threadPrincipal.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\nLista final: " + threadPrincipal.getListaFinal());
    }
}