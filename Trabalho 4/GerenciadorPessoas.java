import java.util.ArrayList;
import java.util.List;

public class GerenciadorPessoas {
    private ArrayList<Pessoa> listaPessoas = new ArrayList<>();

    public boolean cadastrar(String nome, String email) {
        for (Pessoa p : listaPessoas) {
            if (p.getEmail().equals(email)) return false;
        }
        listaPessoas.add(new Pessoa(nome, email));
        return true;
    }

    public Pessoa buscarPorEmail(String email) {
        for (Pessoa p : listaPessoas) {
            if (p.getEmail().equals(email)) return p;
        }
        return null;
    }

    public List<Pessoa> getTodasPessoas() {
        return listaPessoas;
    }
}