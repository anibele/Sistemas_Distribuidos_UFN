import java.io.Serializable;
import java.util.Objects;

public class Pessoa implements Serializable, Comparable<Pessoa> {
    private String nome;
    private String email;

    public Pessoa(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return nome + " - " + email; 
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (getClass() != obj.getClass()) return false;
        final Pessoa other = (Pessoa) obj;
        if (!Objects.equals(this.nome, other.nome)) return false;
        return Objects.equals(this.email, other.email);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(nome, email);
    }

    @Override
    public int compareTo(Pessoa outra) {
        return this.nome.compareToIgnoreCase(outra.nome);
    }
}