import java.awt.*;
import javax.swing.*;

public class ServidorView extends JFrame {
    private JTextArea areaTexto;

    public ServidorView() {
        setTitle("Servidor de E-mails");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        areaTexto = new JTextArea();
        areaTexto.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaTexto);

        add(new JLabel("Lista de Pessoas (Nome - E-mail):"), BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
    }

    public void atualizarTexto(String texto) {
        areaTexto.setText(texto);
    }
}