import java.util.ArrayList;
import javax.swing.SwingUtilities;

public class ServidorController {
    private ServidorView view;
    private ServidorService service;

    public ServidorController() {
        view = new ServidorView();
        view.setVisible(true);
        
        service = new ServidorService(this);
        service.start();
    }

    // Chamado pelo Service quando a lista é alterada
    public void atualizarListaNaView(ArrayList<Pessoa> lista) {
        StringBuilder sb = new StringBuilder();
        for (Pessoa p : lista) {
            sb.append(p.toString()).append("\n"); 
        }
        
        SwingUtilities.invokeLater(() -> {
            view.atualizarTexto(sb.toString());
        });
    }

    public static void main(String[] args) {
        new ServidorController();
    }
}