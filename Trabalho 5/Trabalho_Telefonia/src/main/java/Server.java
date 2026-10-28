import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Server {

    String HOST_URL = "rmi://localhost/BuscarSessao";

    public Server() {
        try {
            LocateRegistry.createRegistry(1099);

            BuscarSessao objetoRemoto = new BuscarSessao();

            Naming.rebind(HOST_URL, objetoRemoto);

            System.out.println(
                    "Servidor online e a espera de clientes...");

        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }

    public static void main(String args[]) {
        new Server();
    }
}
