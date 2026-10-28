/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author gusta
 */

import java.awt.FlowLayout;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.rmi.Naming;
import javax.swing.*;

public class MenuPrincipal extends JFrame {

    private JTextField txtNumero;
    private JTextArea txtJson;

    private JLabel lblSessionId;
    private JLabel lblImsi;
    private JLabel lblImei;
    private JLabel lblMcc;
    private JLabel lblMnc;
    private JLabel lblCellId;
    private JLabel lblLac;
    private JLabel lblBearer;
    private JLabel lblStartTime;
    private JLabel lblEndTime;
    private JLabel lblDuration;
    private JLabel lblIpAddr;
    private JLabel lblSipCallId;
    private JLabel lblCodec;
    private JLabel lblBytesUp;
    private JLabel lblBytesDown;
    private JLabel lblLat;
    private JLabel lblLon;
    private JLabel lblEncryption;

    private IBuscarSessao objetoRemoto;

    public MenuPrincipal() {
        setTitle("Sistema de Telefonia");
        setSize(800, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        try {
            objetoRemoto = (IBuscarSessao) Naming.lookup(
                    "rmi://localhost/BuscarSessao");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this, "Não foi possível conectar ao servidor.");
        }

        montarTela();
    }

    private void montarTela() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));

        //JPanel painelBusca = new JPanel();
        JPanel painelBusca = new JPanel(new FlowLayout(FlowLayout.LEFT));

        txtNumero = new JTextField(18);
        JButton btnBuscar = new JButton("Buscar");
        
        painelBusca.add(new JLabel("Número do celular:"));
        painelBusca.add(txtNumero);
        painelBusca.add(btnBuscar);

        btnBuscar.addActionListener(e -> buscarSessao());

        JPanel painelDados = new JPanel(new GridLayout(0, 2, 5, 5));

        lblSessionId = adicionarCampo(painelDados, "Session ID:");
        lblImsi = adicionarCampo(painelDados, "IMSI:");
        lblImei = adicionarCampo(painelDados, "IMEI:");
        lblMcc = adicionarCampo(painelDados, "MCC:");
        lblMnc = adicionarCampo(painelDados, "MNC:");
        lblCellId = adicionarCampo(painelDados, "Cell ID:");
        lblLac = adicionarCampo(painelDados, "LAC:");
        lblBearer = adicionarCampo(painelDados, "Bearer:");
        lblStartTime = adicionarCampo(painelDados, "Start Time:");
        lblEndTime = adicionarCampo(painelDados, "End Time:");
        lblDuration = adicionarCampo(painelDados, "Duration (s):");
        lblIpAddr = adicionarCampo(painelDados, "IP Address:");
        lblSipCallId = adicionarCampo(painelDados, "SIP Call ID:");
        lblCodec = adicionarCampo(painelDados, "Codec:");
        lblBytesUp = adicionarCampo(painelDados, "Bytes Up:");
        lblBytesDown = adicionarCampo(painelDados, "Bytes Down:");
        lblLat = adicionarCampo(painelDados, "Latitude:");
        lblLon = adicionarCampo(painelDados, "Longitude:");
        lblEncryption = adicionarCampo(painelDados, "Encryption:");

        txtJson = new JTextArea(8, 50);
        txtJson.setEditable(false);
        txtJson.setLineWrap(true);
        txtJson.setWrapStyleWord(true);

        JPanel painelJson = new JPanel(new BorderLayout());
        painelJson.add(new JLabel("JSON da sessão:"), BorderLayout.NORTH);
        painelJson.add(new JScrollPane(txtJson), BorderLayout.CENTER);

        painelPrincipal.setBorder(
                BorderFactory.createEmptyBorder(10, 10, 10, 10));

        painelPrincipal.add(painelBusca, BorderLayout.NORTH);
        painelPrincipal.add(new JScrollPane(painelDados), BorderLayout.CENTER);
        painelPrincipal.add(painelJson, BorderLayout.SOUTH);

        setContentPane(painelPrincipal);
    }

    private JLabel adicionarCampo(JPanel painel, String titulo) {
        painel.add(new JLabel(titulo));
        JLabel valor = new JLabel("-");
        painel.add(valor);
        return valor;
    }

    private void buscarSessao() {
        String numero = txtNumero.getText().trim();

        if (numero.isEmpty()) {
            JOptionPane.showMessageDialog(
                    this, "Informe um número de celular.");
            return;
        }

        if (objetoRemoto == null) {
            JOptionPane.showMessageDialog(
                    this, "O servidor não está conectado.");
            return;
        }

        limparCampos();

        try {
            Sessao sessao = objetoRemoto.buscarSessao(numero);

            if (sessao == null) {
                JOptionPane.showMessageDialog(
                        this, "Nenhuma sessão encontrada para esse número.");
                return;
            }

            String json = sessao.getDadosSessao();
            txtJson.setText(json);

            RegistroTelefonia registro =
                    ConversorJson.converterParaObjeto(json);

            if (registro == null) {
                JOptionPane.showMessageDialog(
                        this, "Não foi possível interpretar os dados da sessão.");
                return;
            }

            lblSessionId.setText(valor(registro.getSession_id()));
            lblImsi.setText(valor(registro.getImsi()));
            lblImei.setText(valor(registro.getImei()));
            lblMcc.setText(valor(registro.getMcc()));
            lblMnc.setText(valor(registro.getMnc()));
            lblCellId.setText(valor(registro.getCell_id()));
            lblLac.setText(valor(registro.getLac()));
            lblBearer.setText(valor(registro.getBearer()));
            lblStartTime.setText(valor(registro.getStart_time()));
            lblEndTime.setText(valor(registro.getEnd_time()));
            lblDuration.setText(valor(registro.getDuration_s()));
            lblIpAddr.setText(valor(registro.getIp_addr()));
            lblSipCallId.setText(valor(registro.getSip_call_id()));
            lblCodec.setText(valor(registro.getCodec()));
            lblBytesUp.setText(valor(registro.getBytes_up()));
            lblBytesDown.setText(valor(registro.getBytes_down()));
            lblLat.setText(valor(registro.getLat()));
            lblLon.setText(valor(registro.getLon()));
            lblEncryption.setText(valor(registro.getEncryption()));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "Erro ao consultar o servidor: " + e.getMessage());
        }
    }

    private String valor(String texto) {
        return texto == null ? "-" : texto;
    }

    private void limparCampos() {
        txtJson.setText("");

        lblSessionId.setText("-");
        lblImsi.setText("-");
        lblImei.setText("-");
        lblMcc.setText("-");
        lblMnc.setText("-");
        lblCellId.setText("-");
        lblLac.setText("-");
        lblBearer.setText("-");
        lblStartTime.setText("-");
        lblEndTime.setText("-");
        lblDuration.setText("-");
        lblIpAddr.setText("-");
        lblSipCallId.setText("-");
        lblCodec.setText("-");
        lblBytesUp.setText("-");
        lblBytesDown.setText("-");
        lblLat.setText("-");
        lblLon.setText("-");
        lblEncryption.setText("-");
    }

    public static void main(String args[]) {
        SwingUtilities.invokeLater(() -> {
            new MenuPrincipal().setVisible(true);
        });
    }
}
