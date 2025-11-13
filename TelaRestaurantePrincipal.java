package main;

import javax.swing.*;
import java.awt.*;

/**
 * Tela principal do restaurante.
 * 
 * <p>Exibe as opções de gerenciamento do restaurante, incluindo:
 * <ul>
 *   <li>Gerenciar cardápio</li>
 *   <li>Visualizar pedidos recebidos</li>
 *   <li>Alternar status de funcionamento</li>
 *   <li>Encerrar sessão</li>
 * </ul>
 * </p>
 */
public class TelaRestaurantePrincipal extends JFrame {

    private Gerenciador gerenciador;
    private Restaurante restaurante;
    private JLabel labelBemVindo;
    private JLabel labelStatus;

    /**
     * Construtor da tela principal do restaurante.
     *
     * @param gerenciador Gerenciador principal do sistema.
     * @param restaurante Restaurante autenticado.
     */
    public TelaRestaurantePrincipal(Gerenciador gerenciador, Restaurante restaurante) {
        this.gerenciador = gerenciador;
        this.restaurante = restaurante;
        inicializarComponentes();
    }

    /**
     * Inicializa os componentes da interface.
     */
    private void inicializarComponentes() {
        setTitle("iFood - Painel do Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Cabeçalho
        labelBemVindo = new JLabel("Bem-vindo, " + restaurante.getNome() + "!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        labelBemVindo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelBemVindo.setForeground(new Color(220, 53, 69));
        painelPrincipal.add(labelBemVindo);

        labelStatus = new JLabel(formatarStatusRestaurante());
        labelStatus.setFont(new Font("Arial", Font.PLAIN, 14));
        labelStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelStatus.setForeground(corStatus());
        painelPrincipal.add(labelStatus);
        painelPrincipal.add(Box.createVerticalStrut(25));

        // Botões de ação
        painelPrincipal.add(criarBotao("GERENCIAR CARDÁPIO", new Color(0, 123, 255), e -> abrirTelaGerenciarCardapio()));
        painelPrincipal.add(Box.createVerticalStrut(15));

        painelPrincipal.add(criarBotao("VER PEDIDOS RECEBIDOS", new Color(40, 167, 69), e -> abrirTelaVerPedidos()));
        painelPrincipal.add(Box.createVerticalStrut(15));

        painelPrincipal.add(criarBotao("ABRIR/FECHAR RESTAURANTE", new Color(255, 193, 7), e -> alternarStatusRestaurante()));
        painelPrincipal.add(Box.createVerticalStrut(15));

        painelPrincipal.add(criarBotao("SAIR", new Color(220, 53, 69), e -> sair()));

        setContentPane(painelPrincipal);
    }

    /**
     * Cria um botão padronizado com estilo e ação definidos.
     *
     * @param texto Texto exibido no botão.
     * @param cor   Cor de fundo do botão.
     * @param acao  Ação executada ao clicar.
     * @return JButton configurado.
     */
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

    /**
     * Abre a tela de gerenciamento de cardápio.
     */
    private void abrirTelaGerenciarCardapio() {
        new TelaGerenciarCardapio(gerenciador, restaurante, this).setVisible(true);
    }

    /**
     * Abre a tela de pedidos recebidos.
     */
    private void abrirTelaVerPedidos() {
        new TelaVerPedidosRestaurante(gerenciador, restaurante, this).setVisible(true);
    }

    /**
     * Alterna o status do restaurante (aberto/fechado)
     * e atualiza a interface.
     */
    private void alternarStatusRestaurante() {
        restaurante.setAberto(!restaurante.isAberto());
        gerenciador.salvarDados();

        labelStatus.setText(formatarStatusRestaurante());
        labelStatus.setForeground(corStatus());

        JOptionPane.showMessageDialog(
            this,
            "Status atualizado: " + (restaurante.isAberto() ? "ABERTO" : "FECHADO"),
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Atualiza o texto exibido conforme o status do restaurante.
     *
     * @return Texto formatado de status.
     */
    private String formatarStatusRestaurante() {
        return "Restaurante: " + (restaurante.isAberto() ? "ABERTO" : "FECHADO");
    }

    /**
     * Define a cor de exibição do status (verde para aberto, vermelho para fechado).
     *
     * @return Cor correspondente ao status atual.
     */
    private Color corStatus() {
        return restaurante.isAberto() ? new Color(40, 167, 69) : new Color(220, 53, 69);
    }

    /**
     * Encerra a sessão atual e retorna à tela de login.
     */
    private void sair() {
        gerenciador.salvarDados();
        gerenciador.logout();
        new TelaLogin(gerenciador).setVisible(true);
        dispose();
    }
}
