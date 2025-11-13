package main;

import javax.swing.*;
import java.awt.*;

/**
 * Tela Principal do Cliente.
 * Menu de opções para o cliente fazer pedidos, ver histórico, etc.
 */
public class TelaClientePrincipal extends JFrame {

    private Gerenciador gerenciador;
    private Cliente cliente;
    private JLabel labelBemVindo;
    private JLabel labelSaldo;

    public TelaClientePrincipal(Gerenciador gerenciador, Cliente cliente) {
        this.gerenciador = gerenciador;
        this.cliente = cliente;
        inicializarComponentes();
    }

    private void inicializarComponentes() {
        setTitle("iFood - Menu Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        labelBemVindo = new JLabel("Bem-vindo, " + cliente.getNome() + "!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        labelBemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelBemVindo.setForeground(new Color(40, 167, 69));
        painelPrincipal.add(labelBemVindo);

        labelSaldo = new JLabel(String.format("Saldo: R$ %.2f", cliente.getCredito()));
        labelSaldo.setFont(new Font("Arial", Font.PLAIN, 14));
        labelSaldo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelSaldo);
        painelPrincipal.add(Box.createVerticalStrut(25));

        painelPrincipal.add(criarBotao("FAZER NOVO PEDIDO", new Color(40, 167, 69), e -> abrirTelaFazerPedido()));
        painelPrincipal.add(Box.createVerticalStrut(15));

        painelPrincipal.add(criarBotao("VER MEUS PEDIDOS", new Color(0, 123, 255), e -> abrirTelaVerPedidos()));
        painelPrincipal.add(Box.createVerticalStrut(15));

        painelPrincipal.add(criarBotao("ADICIONAR CRÉDITO", new Color(255, 193, 7), e -> abrirTelaAdicionarCredito()));
        painelPrincipal.add(Box.createVerticalStrut(15));

        painelPrincipal.add(criarBotao("SAIR", new Color(220, 53, 69), e -> sair()));

        setContentPane(painelPrincipal);
    }

    private JButton criarBotao(String texto, Color cor, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        botao.addActionListener(acao);
        return botao;
    }

    private void abrirTelaFazerPedido() {
        new TelaFazerPedido(gerenciador, cliente, this).setVisible(true);
    }

    private void abrirTelaVerPedidos() {
        new TelaVerPedidosCliente(cliente, this).setVisible(true);
    }

    private void abrirTelaAdicionarCredito() {
        JFrame telaCredito = new JFrame("Adicionar Crédito");
        telaCredito.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaCredito.setSize(400, 250);
        telaCredito.setLocationRelativeTo(this);
        telaCredito.setResizable(false);

        JPanel painel = new JPanel();
        painel.setBackground(new Color(240, 240, 240));
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel label = new JLabel("Valor a adicionar (R$):");
        label.setFont(new Font("Arial", Font.PLAIN, 12));
        painel.add(label);

        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        painel.add(campo);
        painel.add(Box.createVerticalStrut(20));

        JButton botaoAdicionar = new JButton("ADICIONAR");
        botaoAdicionar.setBackground(new Color(40, 167, 69));
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFocusPainted(false);
        botaoAdicionar.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(campo.getText().trim());
                if (valor > 0) {
                    cliente.adicionarCredito(valor);
                    gerenciador.salvarDados();
                    labelSaldo.setText(String.format("Saldo: R$ %.2f", cliente.getCredito()));
                    JOptionPane.showMessageDialog(telaCredito,
                            "Crédito adicionado com sucesso!",
                            "Sucesso",
                            JOptionPane.INFORMATION_MESSAGE);
                    telaCredito.dispose();
                } else {
                    JOptionPane.showMessageDialog(telaCredito,
                            "O valor deve ser positivo!",
                            "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(telaCredito,
                        "Valor inválido!",
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        painel.add(botaoAdicionar);
        telaCredito.setContentPane(painel);
        telaCredito.setVisible(true);
    }

    private void sair() {
        gerenciador.salvarDados();
        gerenciador.logout();
        new TelaLogin(gerenciador).setVisible(true);
        dispose();
    }
}
