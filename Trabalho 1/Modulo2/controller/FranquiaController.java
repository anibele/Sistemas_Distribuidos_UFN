package Atividade1.Modulo2.controller;

import Atividade1.Modulo2.service.FranquiaService;
import Atividade1.Modulo2.view.FranquiaView;
import java.util.concurrent.ThreadLocalRandom;

public class FranquiaController {
    int randomico = ThreadLocalRandom.current().nextInt(9000, 9999 + 1);
    private FranquiaView view;
    private FranquiaService service;

    public FranquiaController(FranquiaView view, FranquiaService service) {
        this.view = view;
        this.service = service;
    }

    public void calcularFaturamentos() {

        try {
            double faturamentoTotal = service.calcularFaturamentoTotal(4, randomico);
            view.exibirFaturamentoFinal(faturamentoTotal);

        } catch (InterruptedException e) {
            view.exibirMensagem("Erro!");
        }
    }
}
