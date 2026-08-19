package Atividade1.Modulo1;

import Atividade1.Modulo1.controller.CaixaController;
import Atividade1.Modulo1.model.CaixaModel;
import Atividade1.Modulo1.service.CaixaService;
import Atividade1.Modulo1.view.CaixaView;

public class Main {
    static void main(String[] args) {
        CaixaModel caixa = new CaixaModel();
        CaixaService caixaService = new CaixaService(caixa);
        CaixaView caixaView = new CaixaView();
        CaixaController caixaController = new CaixaController(caixaService, caixaView);
        caixaController.iniciar();
    }
}
