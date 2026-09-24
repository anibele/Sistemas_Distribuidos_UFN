import java.net.Socket;

public class ThreadRecebedora implements Runnable {

    private Socket socket;

    public ThreadRecebedora(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.println("Thread de recebimento iniciada para: " + socket.getInetAddress());
        try {
            while (true) {
                String mensagem = Comunicador.recebeMensagem(socket);
                if (mensagem == null) {
                    System.out.println("Conexão encerrada com: " + socket.getInetAddress());
                    break; // Sai do laço e encerra a Thread
                }
                System.out.println("[" + socket.getInetAddress() + "]: " + mensagem);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}