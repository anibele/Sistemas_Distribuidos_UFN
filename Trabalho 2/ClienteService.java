
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteService {
    
    public Pessoa solicitarEmail(String nome) throws Exception {
        int porta = 50000;
        Socket cliente = new Socket("localhost", porta);
        
        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
        saida.flush();
        saida.writeObject(nome);
        
        ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
        Pessoa p = (Pessoa) entrada.readObject();
        
        entrada.close();
        saida.close();
        cliente.close();
        
        return p;
    }
}