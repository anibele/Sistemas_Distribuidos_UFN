package Atividade1.Modulo1.service;

import Atividade1.Modulo1.model.CaixaModel;

public class CaixaService {
    private final CaixaModel caixaModel;

    public CaixaService(CaixaModel caixaModel) {
        this.caixaModel = caixaModel;
    }

    public void venderFicha() {
        for (int i = 0; i < 1000; i++) { // Venda de 1000 fichas
            caixaModel.adicionarVenda(10); // Valor da ficha = R$10
        }
    }

    public double verSaldo() {
        return caixaModel.getSaldoCentral();
    }
}
