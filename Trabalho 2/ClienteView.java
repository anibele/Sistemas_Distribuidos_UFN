import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClienteView extends JFrame {
    private JTextField txtNome;
    private JTextField txtEmail;
    private JButton btnEnviar;

    public ClienteView() {
        setTitle("Cliente TCP");
        setSize(350, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel(" Nome Completo:"));
        txtNome = new JTextField();
        add(txtNome);

        add(new JLabel(" E-mail Gerado:"));
        txtEmail = new JTextField();
        txtEmail.setEditable(false); 
        add(txtEmail);

        btnEnviar = new JButton("Enviar");
        add(new JLabel("")); 
        add(btnEnviar);
    }

    public String getNomeInput() {
        return txtNome.getText();
    }

    public void setEmailOutput(String email) {
        txtEmail.setText(email);
    }

    public void limparCampos() {
        txtNome.setText("");
        txtEmail.setText("");
    }

    public void setBotaoListener(ActionListener listener) {
        btnEnviar.addActionListener(listener);
    }

    public void exibirMensagem(String mensagem, String titulo, int tipo) {
        JOptionPane.showMessageDialog(this, mensagem, titulo, tipo);
    }
}