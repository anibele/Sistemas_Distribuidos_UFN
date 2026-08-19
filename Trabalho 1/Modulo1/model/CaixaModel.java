package Atividade1.Modulo1.model;

public class CaixaModel {
    private double saldoCentral = 0.0;

    public synchronized void adicionarVenda(double valor) {
        saldoCentral += valor;
    }

    public synchronized double getSaldoCentral() {
        return saldoCentral;
    }
}
