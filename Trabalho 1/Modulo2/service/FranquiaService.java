package Atividade1.Modulo2.service;

import Atividade1.Modulo2.model.Filial;

import java.util.ArrayList;
import java.util.List;

public class FranquiaService {
    public double calcularFaturamentoTotal(int numeroFiliais, int registrosPorFilial) throws InterruptedException {
        List<Filial> filiais = new ArrayList<>();

        for (int i = 1; i <= numeroFiliais; i++) {
            List<Double> vendasDaFilial = gerarVendas(registrosPorFilial);
            Filial filial = new Filial("Filial " + i, vendasDaFilial);
            filiais.add(filial);
            filial.start();
        }

        double faturamentoTotal = 0.0;

        for (Filial filial : filiais) {
            filial.join();
            faturamentoTotal += filial.getFaturamentoLocal();
        }

        return faturamentoTotal;
    }

    private List<Double> gerarVendas(int quantidade) {
        List<Double> vendas = new ArrayList<>();
        for (int i = 0; i < quantidade; i++) {
            vendas.add(100.0);
        }
        return vendas;
    }
}
