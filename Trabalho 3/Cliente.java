import java.io.IOException;
import java.net.Socket;

public class Cliente {

    private Socket socket;

    public Cliente() {
        criaClientSocket();
        
        // Passando a classe que implementa Runnable para a Thread
        Thread tr = new Thread(new ThreadRecebedora(socket));
        Thread te = new Thread(new ThreadEnviadora(socket));
        
        tr.start();
        te.start();
    }

    private void criaClientSocket() {
        try {
            socket = new Socket("127.0.0.1", 1234); // Modifique o IP se necessário
            System.out.println("Conectado ao Servidor com sucesso!");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Cliente();
    }
}