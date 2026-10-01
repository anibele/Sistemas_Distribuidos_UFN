public class GerenciadorToken {
    private String tokenAtual = "";
    private String emailTokenAtual = "";
    private long tempoGeracaoToken = 0;

    public String solicitarToken(String email) {
        long tempoAtual = System.currentTimeMillis();
        long diferencaSegundos = (tempoAtual - tempoGeracaoToken) / 1000;

        if (email.equals(emailTokenAtual) && diferencaSegundos < 60) {
            return tokenAtual + " (Reaproveitado)";
        } else {
            tokenAtual = "TKN-" + (int)(Math.random() * 10000);
            emailTokenAtual = email;
            tempoGeracaoToken = tempoAtual;
            return tokenAtual + " (Novo)";
        }
    }
}