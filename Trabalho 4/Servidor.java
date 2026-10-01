import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Servidor {
    public static void main(String[] args) {
        ServidorView view = new ServidorView();
        ServidorController controller = new ServidorController(view);
        view.setController(controller);
        view.setVisible(true);

        try (DatagramSocket socket = new DatagramSocket(1234)) {
            System.out.println("Servidor MVC ativo na porta 1234...");
            
            while (true) {
                DatagramPacket pacote = ComunicadorUDP.recebeMensagem(socket);
                if (pacote != null) {
                    String mensagem = new String(pacote.getData()).trim();
                    String resposta = controller.processarRequisicao(mensagem);
                    
                    DatagramPacket pacoteResposta = ComunicadorUDP.montaMensagem(
                        resposta, 
                        pacote.getAddress().getHostAddress(), 
                        pacote.getPort()
                    );
                    ComunicadorUDP.enviaMensagem(socket, pacoteResposta);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}