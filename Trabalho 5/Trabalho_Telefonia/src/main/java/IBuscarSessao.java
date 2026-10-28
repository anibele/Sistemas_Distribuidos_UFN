import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IBuscarSessao extends Remote {

    public Sessao buscarSessao(String numeroCelular)
            throws RemoteException;
}
