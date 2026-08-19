package Atividade1.Modulo1.controller;

import Atividade1.Modulo1.service.CaixaService;
import Atividade1.Modulo1.view.CaixaView;

public class CaixaController {
    private final CaixaService caixaService;
    private final CaixaView caixaView;

    public CaixaController(CaixaService caixaService, CaixaView caixaView) {
        this.caixaService = caixaService;
        this.caixaView = caixaView;
    }

    public void iniciar() {
        Thread[] caixas = new Thread[5]; // Número de caixas
        for (int i = 0; i < 5; i++) {
            final int numeroCaixa = i + 1;
            caixas[i] = new Thread(() -> {
                caixaService.venderFicha();

                caixaView.exibirMensagem(
                        "Caixa " + numeroCaixa + " vendida com sucesso!"
                );
            });
            caixas[i].start();
        }

        for (Thread caixa : caixas) {
            try {
                caixa.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                caixaView.exibirMensagem("Thread interrompida!");
            }
        }

        double saldo = caixaService.verSaldo();
        caixaView.exibirSaldo(saldo);

        if (saldo == 50000.00) {
            caixaView.exibirMensagem("✓ Saldo correto!");
        } else {
            caixaView.exibirMensagem("✗ Saldo incorreto!");
        }
    }
}
