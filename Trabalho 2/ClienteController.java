import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;

public class ClienteController {
    private ClienteView view;
    private ClienteService service;

    public ClienteController() {
        view = new ClienteView();
        service = new ClienteService();

        // Configura o evento do botão
        view.setBotaoListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enviarDados();
            }
        });

        view.setVisible(true);
    }

    private void enviarDados() {
        String nome = view.getNomeInput().trim();
        
        if (nome.isEmpty() || !nome.contains(" ")) {
            view.exibirMensagem("Digite nome e sobrenome!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            Pessoa p = service.solicitarEmail(nome);
            
            if (p == null) {
                view.exibirMensagem("Seu nome já está na lista com um email gerado.", "Atenção", JOptionPane.WARNING_MESSAGE);
                view.setEmailOutput("Email já existe!");
            } else {
                view.setEmailOutput(p.getEmail());
                view.exibirMensagem("Pessoa criada e recebida com sucesso!\nEmail: " + p.getEmail(), "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            view.exibirMensagem("Erro ao conectar com o servidor: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new ClienteController();
    }
}