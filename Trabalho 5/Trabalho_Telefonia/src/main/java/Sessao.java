import java.io.Serializable;

public class Sessao implements Serializable {

    String numeroCelular;
    String dadosSessao;

    public Sessao(String numeroCelular, String dadosSessao) {
        this.numeroCelular = numeroCelular;
        this.dadosSessao = dadosSessao;
    }

    public String getNumeroCelular() {
        return numeroCelular;
    }

    public void setNumeroCelular(String numeroCelular) {
        this.numeroCelular = numeroCelular;
    }

    public String getDadosSessao() {
        return dadosSessao;
    }

    public void setDadosSessao(String dadosSessao) {
        this.dadosSessao = dadosSessao;
    }

    @Override
    public String toString() {
        return "Sessao{" + "numeroCelular=" + numeroCelular
                + ", dadosSessao=" + dadosSessao + '}';
    }
}
