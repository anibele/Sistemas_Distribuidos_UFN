import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Comunicador {

    public static String recebeMensagem(Socket s) {
        try {
            DataInputStream leitor = new DataInputStream(s.getInputStream());
            return leitor.readUTF();
        } catch (Exception e) {
            return null; // Retorna nulo se o socket fechar
        }
    }

    public static void enviaMensagem(Socket s, String mensagem) {
        try {
            DataOutputStream escritor = new DataOutputStream(s.getOutputStream());
            escritor.writeUTF(mensagem);
            escritor.flush();
        } catch (Exception e) {
            // Tratamento opcional
        }
    }
}