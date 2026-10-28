
import java.rmi.Naming;
import javax.swing.JOptionPane;

public class Client {

    public static void main(String args[]) {
        try {
            IBuscarSessao objetoRemoto =
                    (IBuscarSessao) Naming.lookup(
                            "rmi://localhost/BuscarSessao");

            String numero = JOptionPane.showInputDialog(
                    null, "Informe o número do celular");

            if (numero == null || numero.trim().isEmpty()) {
                return;
            }

            Sessao sessao = objetoRemoto.buscarSessao(numero.trim());

            if (sessao != null) {
                JOptionPane.showMessageDialog(
                        null, sessao.getDadosSessao());
            } else {
                JOptionPane.showMessageDialog(
                        null,
                        "Nenhuma sessão encontrada para esse número.",
                        "Consulta",
                        JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e);
            JOptionPane.showMessageDialog(
                    null,
                    "Não foi possível conectar ao servidor.");
        }
    }
}
