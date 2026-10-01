import java.net.DatagramPacket;
import java.net.DatagramSocket;
import javax.swing.*;

public class ClienteController {
    private ClienteView view;
    private DatagramSocket socket;
    private Timer timerToken;
    private String emailLogado;

    public ClienteController(ClienteView view) {
        this.view = view;
        try {
            this.socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }

        configurarEventos();
    }

    private void configurarEventos() {
        view.addLoginListener(e -> fazerLogin());
        
        view.addVoltarListener(e -> view.mostrarTela("TELA_LOGIN"));
        
        view.addCadastrarListener(e -> {
            String resposta = enviarMensagem("CADASTRO;" + view.getCadastroNome() + ";" + view.getCadastroEmail());
            if (resposta.startsWith("SUCESSO")) {
                view.exibirMensagem("Conta criada! Pode fazer login.");
                view.mostrarTela("TELA_LOGIN");
            } else {
                view.exibirMensagem(resposta.split(";")[1]);
            }
        });
    }

    private void fazerLogin() {
        emailLogado = view.getLoginEmail();
        if (emailLogado.isEmpty()) return;

        String resposta = enviarMensagem("LOGIN;" + emailLogado);
        
        if (resposta.equals("LOGIN_OK")) {
            view.mostrarTela("TELA_TOKEN");
            iniciarLoopDeToken();
        } else {
            if (view.confirmar("Conta não encontrada. Deseja criar uma nova conta?")) {
                view.mostrarTela("TELA_CADASTRO");
            }
        }
    }

    private void iniciarLoopDeToken() {
        timerToken = new Timer(1000, e -> {
            // Usa Thread separada para não travar a animação da bolinha
            new Thread(() -> {
                String resposta = enviarMensagem("TOKEN;" + emailLogado);
                if (resposta.startsWith("TOKEN")) {
                    String[] partes = resposta.split(";");
                    String tokenStr = partes[1];
                    int tempo = Integer.parseInt(partes[2]);
                    
                    SwingUtilities.invokeLater(() -> view.atualizarBolinha(tokenStr, tempo));
                }
            }).start();
        });
        timerToken.start();
    }

    // Método síncrono que bloqueia esperando resposta (seguro isolar em Threads)
    private String enviarMensagem(String mensagem) {
        try {
            DatagramPacket pacoteEnvio = ComunicadorUDP.montaMensagem(mensagem, "localhost", 1234);
            ComunicadorUDP.enviaMensagem(socket, pacoteEnvio);
            DatagramPacket pacoteResposta = ComunicadorUDP.recebeMensagem(socket);
            if (pacoteResposta != null) {
                return new String(pacoteResposta.getData()).trim();
            }
        } catch (Exception ex) {
            return "ERRO;Falha na conexão";
        }
        return "ERRO;Sem resposta";
    }
}