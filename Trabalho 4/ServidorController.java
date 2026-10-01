public class ServidorController {
    private GerenciadorPessoas gerenciador = new GerenciadorPessoas();
    private ServidorView view;

    public ServidorController(ServidorView view) {
        this.view = view;
    }

    public String processarRequisicao(String mensagem) {
        String[] partes = mensagem.split(";");
        String comando = partes[0];

        if (comando.equals("LOGIN")) {
            Pessoa p = gerenciador.buscarPorEmail(partes[1]);
            return p != null ? "LOGIN_OK" : "ERRO;Utilizador não encontrado!";
            
        } else if (comando.equals("CADASTRO")) {
            boolean sucesso = gerenciador.cadastrar(partes[1], partes[2]);
            view.atualizarTabela(gerenciador.getTodasPessoas());
            return sucesso ? "SUCESSO;Conta criada!" : "ERRO;E-mail já registado!";
            
        } else if (comando.equals("TOKEN")) {
            String email = partes[1];
            Pessoa p = gerenciador.buscarPorEmail(email);
            if (p == null) return "ERRO;Registe-se primeiro!";

            long tempoAtual = System.currentTimeMillis();
            long passado = (tempoAtual - p.getTempoGeracaoToken()) / 1000;
            long restante = 20 - passado;

            if (p.getTempoGeracaoToken() > 0 && passado < 20) {
                return "TOKEN;" + p.getToken() + ";" + restante;
            } else {
                String novoToken = "TKN-" + (int)(Math.random() * 10000);
                p.setToken(novoToken, tempoAtual);
                view.atualizarTabela(gerenciador.getTodasPessoas());
                return "TOKEN;" + novoToken + ";20";
            }
        }
        return "ERRO;Comando desconhecido";
    }
    
    public GerenciadorPessoas getGerenciador() { return gerenciador; }
}