
public class Pessoa {
    private String nome;
    private String email;
    private String token;
    private long tempoGeracaoToken;

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
        this.token = "Sem token";
        this.tempoGeracaoToken = 0;
    }

    public String getNome() { return nome; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
    public long getTempoGeracaoToken() { return tempoGeracaoToken; }

    public void setToken(String token, long tempoGeracaoToken) {
        this.token = token;
        this.tempoGeracaoToken = tempoGeracaoToken;
    }
}