package main;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de Login do sistema iFood.
 * Permite autenticação de Cliente ou Restaurante.
 * 
 * <p>Esta tela é responsável por validar credenciais e direcionar o usuário
 * à respectiva interface (Cliente ou Restaurante) após login bem-sucedido.</p>
 */
public class TelaLogin extends JFrame {
    
    private Gerenciador gerenciador;
    private JTextField campoEmail;
    private JPasswordField campoSenha;
    private JComboBox<String> comboTipoUsuario;
    private JLabel labelStatus;
    
    /**
     * Construtor da tela de login.
     * 
     * @param gerenciador Instância do gerenciador principal do sistema.
     */
    public TelaLogin(Gerenciador gerenciador) {
        this.gerenciador = gerenciador;
        inicializarComponentes();
    }
    
    /**
     * Inicializa todos os componentes da interface.
     */
    private void inicializarComponentes() {
        setTitle("iFood - Sistema de Pedidos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(30, 50, 30, 50));
        
        JLabel labelTitulo = new JLabel("iFood - Login");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitulo.setForeground(new Color(220, 53, 69));
        painelPrincipal.add(labelTitulo);
        painelPrincipal.add(Box.createVerticalStrut(20));
        
        JLabel labelTipo = new JLabel("Tipo de Usuário:");
        labelTipo.setFont(new Font("Arial", Font.PLAIN, 12));
        painelPrincipal.add(labelTipo);
        
        comboTipoUsuario = new JComboBox<>(new String[]{"Cliente", "Restaurante"});
        comboTipoUsuario.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        painelPrincipal.add(comboTipoUsuario);
        painelPrincipal.add(Box.createVerticalStrut(15));
        
        JLabel labelEmail = new JLabel("Email:");
        labelEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        painelPrincipal.add(labelEmail);
        
        campoEmail = new JTextField();
        campoEmail.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoEmail.setFont(new Font("Arial", Font.PLAIN, 12));
        painelPrincipal.add(campoEmail);
        painelPrincipal.add(Box.createVerticalStrut(15));
        
        JLabel labelSenha = new JLabel("Senha:");
        labelSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        painelPrincipal.add(labelSenha);
        
        campoSenha = new JPasswordField();
        campoSenha.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        campoSenha.setFont(new Font("Arial", Font.PLAIN, 12));
        painelPrincipal.add(campoSenha);
        painelPrincipal.add(Box.createVerticalStrut(20));
        
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
        
        JButton botaoEntrar = criarBotao("ENTRAR", new Color(220, 53, 69), e -> autenticar());
        JButton botaoCadastro = criarBotao("CADASTRAR", new Color(40, 167, 69), e -> abrirTelaCadastro());
        
        painelBotoes.add(botaoEntrar);
        painelBotoes.add(Box.createHorizontalStrut(15));
        painelBotoes.add(botaoCadastro);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(Box.createVerticalStrut(15));
        
        labelStatus = new JLabel("");
        labelStatus.setFont(new Font("Arial", Font.ITALIC, 10));
        labelStatus.setForeground(new Color(220, 53, 69));
        labelStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelStatus);
        
        setContentPane(painelPrincipal);
    }

    /**
     * Cria um botão padronizado com estilo e ação definida.
     * 
     * @param texto Texto exibido no botão.
     * @param cor Cor de fundo.
     * @param acao Evento disparado ao clicar.
     * @return JButton configurado.
     */
    private JButton criarBotao(String texto, Color cor, java.awt.event.ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setPreferredSize(new Dimension(120, 40));
        botao.addActionListener(acao);
        return botao;
    }
    
    /**
     * Realiza a autenticação do usuário.
     * Valida os campos e direciona para a tela correspondente.
     */
    private void autenticar() {
        String email = campoEmail.getText().trim();
        String senha = new String(campoSenha.getPassword());
        String tipo = (String) comboTipoUsuario.getSelectedItem();
        
        if (email.isEmpty() || senha.isEmpty()) {
            labelStatus.setText("Preencha todos os campos!");
            return;
        }
        
        try {
            if ("Cliente".equals(tipo)) {
                Cliente cliente = gerenciador.autenticarCliente(email, senha);
                labelStatus.setText("Login bem-sucedido!");
                abrirTelaClientePrincipal(cliente);
            } else {
                Restaurante restaurante = gerenciador.autenticarRestaurante(email, senha);
                labelStatus.setText("Login bem-sucedido!");
                abrirTelaRestaurantePrincipal(restaurante);
            }
        } catch (UsuarioNaoAutenticadoException e) {
            labelStatus.setText(e.getMessage());
            campoSenha.setText("");
        }
    }
    
    /**
     * Abre a tela de cadastro conforme o tipo selecionado.
     */
    private void abrirTelaCadastro() {
        String tipo = (String) comboTipoUsuario.getSelectedItem();
        if ("Cliente".equals(tipo)) {
            new TelaCadastroCliente(gerenciador, this).setVisible(true);
        } else {
            new TelaCadastroRestaurante(gerenciador, this).setVisible(true);
        }
    }
    
    /**
     * Abre a tela principal do cliente autenticado.
     * 
     * @param cliente Instância do cliente autenticado.
     */
    private void abrirTelaClientePrincipal(Cliente cliente) {
        new TelaClientePrincipal(gerenciador, cliente).setVisible(true);
        dispose();
    }
    
    /**
     * Abre a tela principal do restaurante autenticado.
     * 
     * @param restaurante Instância do restaurante autenticado.
     */
    private void abrirTelaRestaurantePrincipal(Restaurante restaurante) {
        new TelaRestaurantePrincipal(gerenciador, restaurante).setVisible(true);
        dispose();
    }
}
