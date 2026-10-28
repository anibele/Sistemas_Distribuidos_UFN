
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BuscarSessao extends UnicastRemoteObject
        implements IBuscarSessao {

    TabelaHash tabela;

    public BuscarSessao() throws RemoteException {
        tabela = new TabelaHash();
        carregarSessoes();
    }

    private void carregarSessoes() {
        try {
            BufferedReader br = new BufferedReader(
                    new FileReader("telephony_sessions.txt"));

            String linha;

            while ((linha = br.readLine()) != null) {
                String[] partes = linha.split(";", 2);

                if (partes.length == 2) {
                    String numero = partes[0];
                    String dados = partes[1];

                    Sessao sessao = new Sessao(numero, dados);
                    tabela.sessoes.put(numero, sessao);
                }
            }

            br.close();

            System.out.println("Base de sessões carregada.");

        } catch (IOException e) {
            System.out.println("Erro ao abrir a base: " + e.getMessage());
        }
    }

    @Override
    public Sessao buscarSessao(String numeroCelular)
            throws RemoteException {

        return tabela.sessoes.get(numeroCelular);
    }
}
