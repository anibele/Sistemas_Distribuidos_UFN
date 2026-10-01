
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class ServidorView extends JFrame {
    private JTable tabela;
    private DefaultTableModel modeloTabela;
    private ServidorController controller;

    public ServidorView() {
        setTitle("Servidor UDP");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Configuração da tabela
        String[] colunas = {"Nome", "E-mail", "Token Atual", "Tempo Restante"};
        modeloTabela = new DefaultTableModel(colunas, 0);
        tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        Timer timer = new Timer(1000, e -> atualizarTempoRestante());
        timer.start();
    }
    
    public void setController(ServidorController controller) {
        this.controller = controller;
    }

    public void atualizarTabela(List<Pessoa> pessoas) {
        modeloTabela.setRowCount(0); 
        for (Pessoa p : pessoas) {
            modeloTabela.addRow(new Object[]{p.getNome(), p.getEmail(), p.getToken(), calcularTempoRestante(p)});
        }
    }

    private void atualizarTempoRestante() {
        if (controller == null) return;
        List<Pessoa> pessoas = controller.getGerenciador().getTodasPessoas();
        for (int i = 0; i < pessoas.size(); i++) {
            modeloTabela.setValueAt(calcularTempoRestante(pessoas.get(i)), i, 3);
        }
    }

    private String calcularTempoRestante(Pessoa p) {
        if (p.getTempoGeracaoToken() == 0) return "-";
        
        long passado = (System.currentTimeMillis() - p.getTempoGeracaoToken()) / 1000;
        long restante = 20 - passado;
        
        return restante > 0 ? restante + "s" : "Expirado";
    }
}