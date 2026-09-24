import java.net.Socket;
import java.util.Scanner;

public class ThreadEnviadora implements Runnable {

    private Socket socket;

    public ThreadEnviadora(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);
        try {
            System.out.println("Pode digitar suas mensagens:");
            while (true) {
                String mensagem = scanner.nextLine();
                Comunicador.enviaMensagem(socket, mensagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}