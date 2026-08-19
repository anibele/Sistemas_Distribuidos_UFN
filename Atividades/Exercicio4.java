/*
#Fazer um código orientado a objetos (Java, C# ou Python) que:
# a) leia o arquivo numeros1.txt (com 10 números, um abaixo do outro) e popule seus números numa lista de inteiros
# b) leia o arquivo numeros2.txt (com 10 números, um abaixo do outro) e popule seus números numa lista de inteiros anterior

#Observação:
# - Implemente threads para as operações de ler/popular
# - Com memória compartilhada
*/

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class LerNumeros implements Runnable {
    private final List<Integer> numeros;
    private final String arquivo;

    public LerNumeros(List<Integer> numeros, String arquivo) {
        this.numeros = numeros;
        this.arquivo = arquivo;
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                numeros.add(Integer.parseInt(linha.trim()));
            }
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler o arquivo: " + arquivo, e);
        }
    }
}

class ExibirNumeros implements Runnable {
    private final List<Integer> numeros;

    public ExibirNumeros(List<Integer> numeros) {
        this.numeros = numeros;
    }

    @Override
    public void run() {
        synchronized (numeros) {
            for (Integer numero : numeros) {
                System.out.println(numero);
            }
        }
    }
}

public class Exercicio4 {
    public static void main(String[] args) {
        List<Integer> numeros = Collections.synchronizedList(new ArrayList<>());

        Thread thread1 = new Thread(new LerNumeros(numeros, "numeros1.txt"));
        Thread thread2 = new Thread(new LerNumeros(numeros, "numeros2.txt"));

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrompida ao aguardar leitura dos arquivos.", e);
        }

        Thread exibirThread = new Thread(new ExibirNumeros(numeros));
        exibirThread.start();

        try {
            exibirThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Thread interrompida ao exibir os números.", e);
        }
    }
}
