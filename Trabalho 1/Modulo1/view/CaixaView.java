package Atividade1.Modulo1.view;

public class CaixaView {

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }

    public void exibirSaldo(double saldo) {
        System.out.printf("Saldo Final: R$ %.2f%n", saldo);
    }
}
