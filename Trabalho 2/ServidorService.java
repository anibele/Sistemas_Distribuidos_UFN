import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;

public class ServidorService extends Thread {
    private ServidorController controller;
    private ArrayList<Pessoa> lista;

    public ServidorService(ServidorController controller) {
        this.controller = controller;
        this.lista = new ArrayList<>();
    }

    @Override
    public void run() {
        try {
            int portaServidor = 50000;
            ServerSocket servidor = new ServerSocket(portaServidor);
            System.out.println("Servidor ouvindo a porta: " + portaServidor);
            
            while (true) {
                boolean encontrado = false;
                Socket cliente = servidor.accept();
                
                ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
                String nomePessoa = (String) entrada.readObject();
                
                String vetorNome[] = nomePessoa.split(" ");
                String email = vetorNome[0] + "." + vetorNome[vetorNome.length - 1] + "@ufn.edu.br";
                nomePessoa = nomePessoa.toUpperCase();
                email = email.toLowerCase();
                
                Pessoa p = new Pessoa(nomePessoa, email);
                
                if (lista.contains(p)) {
                    encontrado = true;
                } else {
                    lista.add(p);
                    Collections.sort(lista); 
                    controller.atualizarListaNaView(lista); 
                }
                
                ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
                saida.flush();
                if (!encontrado) {
                    saida.writeObject(p);
                } else {
                    saida.writeObject(null);
                }
                
                saida.close();
                entrada.close();
                cliente.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        }
    }
}