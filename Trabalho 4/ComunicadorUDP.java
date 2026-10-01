import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class ComunicadorUDP {
    public static DatagramPacket montaMensagem(String mensagem, String ip, int porta) {
        try {
            byte[] buffer = mensagem.getBytes();
            DatagramPacket pacote = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(ip), porta);
            return pacote;
        } catch (UnknownHostException ex) {
            return null;
        } 
    }

    public static DatagramPacket recebeMensagem(DatagramSocket s) {
        try {
            DatagramPacket pacote = new DatagramPacket(new byte[512], 512);
            s.receive(pacote);
            return pacote;
        } catch (Exception e) {
            return null;
        }
    }

    public static void enviaMensagem(DatagramSocket s, DatagramPacket pacote) {
        try {
            s.send(pacote);
        } catch (Exception e) {}
    }
}