/*
fazer um código em java e orientado a objetos que:
a) leia o arquivo numeros.txt e popule seus números (com 10 números, um abaixo do outro) numa lista de int.
b) leia o arquivo nomes.txt e popule seus nomes (com 15 nomes, um abaixo do outro) numa lista de strings.
c) exiba as listas respectivas.
OBS:
- implemente threads para as operações de ler/popular arquivos
- implemente threads para as operações de exibir listas
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exercicio3 {

    static class LerNumeros implements Runnable {
        private final List<Integer> numeros;

        public LerNumeros(List<Integer> numeros) {
            this.numeros = numeros;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader("numeros.txt"))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    numeros.add(Integer.parseInt(linha));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class LerNomes implements Runnable {
        private final List<String> nomes;

        public LerNomes(List<String> nomes) {
            this.nomes = nomes;
        }

        @Override
        public void run() {
            try (BufferedReader br = new BufferedReader(new FileReader("nomes.txt"))) {
                String linha;
                while ((linha = br.readLine()) != null) {
                    nomes.add(linha);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    static class ExibirNumeros implements Runnable {
        private final List<Integer> numeros;

        public ExibirNumeros(List<Integer> numeros) {
            this.numeros = numeros;
        }

        @Override
        public void run() {
            System.out.println("Números:");
            for (Integer numero : numeros) {
                System.out.println(numero);
            }
        }
    }

    static class ExibirNomes implements Runnable {
        private final List<String> nomes;

        public ExibirNomes(List<String> nomes) {
            this.nomes = nomes;
        }

        @Override
        public void run() {
            System.out.println("Nomes:");
            for (String nome : nomes) {
                System.out.println(nome);
            }
        }
    }

    public static void main(String[] args) {
        List<Integer> numeros = new ArrayList<>();
        List<String> nomes = new ArrayList<>();

        Thread threadNumeros = new Thread(new LerNumeros(numeros));
        Thread threadNomes = new Thread(new LerNomes(nomes));

        threadNumeros.start();
        threadNomes.start();

        try {
            threadNumeros.join();
            threadNomes.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread threadExibirNumeros = new Thread(new ExibirNumeros(numeros));
        Thread threadExibirNomes = new Thread(new ExibirNomes(nomes));

        threadExibirNumeros.start();
        threadExibirNomes.start();

        try {
            threadExibirNumeros.join();
            threadExibirNomes.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}


