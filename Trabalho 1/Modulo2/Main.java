package Atividade1.Modulo2;

import Atividade1.Modulo2.controller.FranquiaController;
import Atividade1.Modulo2.service.FranquiaService;
import Atividade1.Modulo2.view.FranquiaView;

public class Main {
    static void main(String[] args) {
        FranquiaView view = new FranquiaView();
        FranquiaService service = new FranquiaService();
        FranquiaController controller = new FranquiaController(view, service);

        controller.calcularFaturamentos();
    }
}
