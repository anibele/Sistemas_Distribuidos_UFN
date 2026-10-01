import java.awt.*;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ClienteView extends JFrame {
    private CardLayout layoutTelas = new CardLayout();
    private JPanel painelPrincipal = new JPanel(layoutTelas);

    //Login
    private JTextField txtLoginEmail = new JTextField(15);
    private JButton btnLogin = new JButton("Entrar");

    //Cadastro
    private JTextField txtCadastroNome = new JTextField(15);
    private JTextField txtCadastroEmail = new JTextField(15);
    private JButton btnCadastrar = new JButton("Salvar Cadastro");
    private JButton btnVoltarLogin = new JButton("Voltar");

    //Token(Bolinha)
    private BolinhaTokenPanel painelBolinha = new BolinhaTokenPanel();

    public ClienteView() {
        setTitle("App Cliente");
        setSize(350, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Centraliza na tela

        // Monta as 3 telas
        painelPrincipal.add(criarTelaLogin(), "TELA_LOGIN");
        painelPrincipal.add(criarTelaCadastro(), "TELA_CADASTRO");
        painelPrincipal.add(criarTelaToken(), "TELA_TOKEN");

        add(painelPrincipal);
    }

    private JPanel criarTelaLogin() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0; p.add(new JLabel("E-mail para Login:"), gbc);
        gbc.gridy = 1; p.add(txtLoginEmail, gbc);
        gbc.gridy = 2; p.add(btnLogin, gbc);
        return p;
    }

    private JPanel criarTelaCadastro() {
        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.gridy = 0; p.add(new JLabel("Nome:"), gbc);
        gbc.gridy = 1; p.add(txtCadastroNome, gbc);
        gbc.gridy = 2; p.add(new JLabel("E-mail:"), gbc);
        gbc.gridy = 3; p.add(txtCadastroEmail, gbc);
        gbc.gridy = 4; p.add(btnCadastrar, gbc);
        gbc.gridy = 5; p.add(btnVoltarLogin, gbc);
        return p;
    }

    private JPanel criarTelaToken() {
        JPanel p = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("O seu Token de Acesso", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        p.add(titulo, BorderLayout.NORTH);
        p.add(painelBolinha, BorderLayout.CENTER);
        return p;
    }

    // Navegação entre telas
    public void mostrarTela(String nomeTela) {
        layoutTelas.show(painelPrincipal, nomeTela);
    }

    // Getters para o Controller
    public String getLoginEmail() { return txtLoginEmail.getText(); }
    public String getCadastroNome() { return txtCadastroNome.getText(); }
    public String getCadastroEmail() { return txtCadastroEmail.getText(); }
    
    // Atualiza a bolinha
    public void atualizarBolinha(String token, int tempoRestante) {
        painelBolinha.setDados(token, tempoRestante);
    }

    // Exibe popups
    public void exibirMensagem(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public boolean confirmar(String msg) {
        Object[] opcoes = {"Sim", "Não"};
        int escolha = JOptionPane.showOptionDialog(
                this, 
                msg, 
                "Atenção", 
                JOptionPane.YES_NO_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                opcoes, 
                opcoes[0]
        );
        
        return escolha == 0; 
    }

    public void addLoginListener(ActionListener l) { btnLogin.addActionListener(l); }
    public void addCadastrarListener(ActionListener l) { btnCadastrar.addActionListener(l); }
    public void addVoltarListener(ActionListener l) { btnVoltarLogin.addActionListener(l); }
}

class BolinhaTokenPanel extends JPanel {
    private String token = "Carregando...";
    private int tempo = 20;

    public void setDados(String token, int tempo) {
        this.token = token;
        this.tempo = tempo;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int tamanho = Math.min(getWidth(), getHeight()) - 40;
        int x = (getWidth() - tamanho) / 2;
        int y = (getHeight() - tamanho) / 2;

        g2.setColor(new Color(220, 220, 220));
        g2.fillOval(x, y, tamanho, tamanho);

        g2.setColor(new Color(41, 128, 185));
        int angulo = (int) ((tempo / 20.0) * 360);
        g2.fillArc(x, y, tamanho, tamanho, 90, angulo);

        g2.setColor(getBackground());
        g2.fillOval(x + 15, y + 15, tamanho - 30, tamanho - 30);

        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 22));
        FontMetrics fm = g2.getFontMetrics();
        g2.drawString(token, x + (tamanho - fm.stringWidth(token)) / 2, y + (tamanho / 2));

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        String textoTempo = tempo + "s";
        g2.drawString(textoTempo, x + (tamanho - g2.getFontMetrics().stringWidth(textoTempo)) / 2, y + (tamanho / 2) + 25);
    }
}