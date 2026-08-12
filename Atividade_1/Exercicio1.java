/*Divisão e Conquista: Soma de Sublistas
Contexto: O processamento de grandes volumes de dados numéricos.
Problema: Dado um vetor ou lista com 100 números inteiros aleatórios, divida essa lista em 4 partes iguais.
Ação: Crie 4 threads. Cada thread recebe apenas uma das partes como parâmetro de entrada, calcula a soma dos elementos dessa sublista e retorna o valor final.
Encerramento: A thread principal aguarda o fim das 4 threads, coleta as 4 somas parciais e calcula a soma total.
 */

import java.util.Random;

public class Exercicio1 {
    public static void main(String[] args) {
        int[] listaoriginal = new int[100];
        Random random = new Random();

        // Preenchendo o vetor com números aleatórios
        for (int i = 0; i < listaoriginal.length; i++) {
            listaoriginal[i] = random.nextInt(100);
        }

        // Dividindo o vetor em 4 partes iguais e guardando posiçoes de início e fim de cada parte
        int tamanhoParte = listaoriginal.length / 4;
        int inicio1 = 0;
        int fim1 = tamanhoParte;
        int inicio2 = fim1;
        int fim2 = inicio2 + tamanhoParte;
        int inicio3 = fim2;
        int fim3 = inicio3 + tamanhoParte;
        int inicio4 = fim3;
        int fim4 = listaoriginal.length;

        // Criando a thread generica para calcular a soma de uma sublista
        class SomaThread extends Thread{
            private int[] sublista;
            private int soma;

            public SomaThread(int[] sublista) {
                this.sublista = sublista;
                this.soma = 0;
            }

            public int getSoma() {
                return soma;
            }

            @Override
            public void run() {
                for (int num : sublista) {
                    soma += num;
                }
            }
        }

        // Criando as 4 threads com suas respectivas sublistas
        SomaThread thread1 = new SomaThread(java.util.Arrays.copyOfRange(listaoriginal, inicio1, fim1));
        SomaThread thread2 = new SomaThread(java.util.Arrays.copyOfRange(listaoriginal, inicio2, fim2));
        SomaThread thread3 = new SomaThread(java.util.Arrays.copyOfRange(listaoriginal, inicio3, fim3));
        SomaThread thread4 = new SomaThread(java.util.Arrays.copyOfRange(listaoriginal, inicio4, fim4));

        // Iniciando as 4 threads
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        // Aguardando o fim das 4 threads
        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // criando a thread para calcular a soma total das 4 somas parciais
        class SomaTotalThread extends Thread {
            private int soma1, soma2, soma3, soma4;
            private int somaTotal = 0;

            public SomaTotalThread(int soma1, int soma2, int soma3, int soma4) {
                this.soma1 = soma1;
                this.soma2 = soma2;
                this.soma3 = soma3;
                this.soma4 = soma4;
            }

            @Override
            public void run() {
                somaTotal = soma1 + soma2 + soma3 + soma4;
            }

            public int getSomaTotal() {
                return somaTotal;
            }
        }
        
        //mostrando as listas e suas somas parciais
        System.out.println("Lista 1: " + java.util.Arrays.toString(java.util.Arrays.copyOfRange(listaoriginal, inicio1, fim1)) + " Soma: " + thread1.getSoma());
        System.out.println("Lista 2: " + java.util.Arrays.toString(java.util.Arrays.copyOfRange(listaoriginal, inicio2, fim2)) + " Soma: " + thread2.getSoma());
        System.out.println("Lista 3: " + java.util.Arrays.toString(java.util.Arrays.copyOfRange(listaoriginal, inicio3, fim3)) + " Soma: " + thread3.getSoma());
        System.out.println("Lista 4: " + java.util.Arrays.toString(java.util.Arrays.copyOfRange(listaoriginal, inicio4, fim4)) + " Soma: " + thread4.getSoma());

        SomaTotalThread threadSomaTotal = new SomaTotalThread(thread1.getSoma(), thread2.getSoma(), thread3.getSoma(), thread4.getSoma());
        threadSomaTotal.start();

        try {
            threadSomaTotal.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Soma total: " + threadSomaTotal.getSomaTotal());
    }
}