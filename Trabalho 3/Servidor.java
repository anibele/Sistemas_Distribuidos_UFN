import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Servidor {

    private ServerSocket servidor;
    private List<Socket> clientesConectados;
    private ExecutorService poolDeThreads;

    public Servidor() {
        clientesConectados = new ArrayList<>();
        // Cria um pool de threads dinâmico que cresce conforme o número de clientes
        poolDeThreads = Executors.newCachedThreadPool(); 
        
        criaServerSocket();
        iniciaThreadEnviadoraDoServidor();
        aguardaClientes();
    }

    private void criaServerSocket() {
        try {
            servidor = new ServerSocket(1234);
            System.out.println("Servidor escutando na porta 1234...");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // O Servidor tem apenas UMA thread lendo do seu console para enviar a todos os clientes
    private void iniciaThreadEnviadoraDoServidor() {
        Thread threadEnviadora = new Thread(() -> {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                String mensagem = scanner.nextLine();
                enviaParaTodos(mensagem); // Envia (broadcast) para todos na lista
            }
        });
        threadEnviadora.start();
    }

    private void enviaParaTodos(String mensagem) {
        for (Socket cliente : clientesConectados) {
            Comunicador.enviaMensagem(cliente, "Servidor diz: " + mensagem);
        }
    }

    private void aguardaClientes() {
        try {
            while (true) {
                // Fica bloqueado até um novo cliente se conectar
                Socket cliente = servidor.accept(); 
                System.out.println("Nova conexão recebida de: " + cliente.getInetAddress());
                
                // Adiciona o novo cliente na lista do servidor
                clientesConectados.add(cliente);
                
                // Submete a tarefa de ouvir ESTE cliente para o Pool de Threads
                poolDeThreads.execute(new ThreadRecebedora(cliente));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Servidor();
    }
}