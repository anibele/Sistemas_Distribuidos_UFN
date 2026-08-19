package Atividade1.Modulo2.model;

import java.util.List;

public class Filial extends Thread {
    private String nome;
    private List<Double> vendas;
    private double faturamentoLocal;

    public Filial(String nome, List<Double> vendas) {
        this.nome = nome;
        this.vendas = vendas;
        this.faturamentoLocal = 0;
    }

    @Override // Atualiza o faturamento local toda vez que for alterado
    public void run() {
        for (Double valor : vendas) {
            this.faturamentoLocal += valor;
        }
    }

    public String getNome() {
        return nome;
    }

    public List<Double> getVendas() {
        return vendas;
    }

    public double getFaturamentoLocal() {
        return faturamentoLocal;
    }
}
