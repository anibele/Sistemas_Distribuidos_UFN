package Atividade1.Modulo2.view;

public class FranquiaView {
    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirFaturamentoFinal(double faturamentoTotal) {
        System.out.printf("Faturamento Total da Franquia: R$ %.2f\n", faturamentoTotal);
    }
}
