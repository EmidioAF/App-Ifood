package main;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de Cadastro de Cliente.
 * 
 * <p>Permite a criação de uma nova conta de cliente no sistema de delivery.
 * Inclui campos para informações pessoais e de contato, além de validação
 * e feedback visual ao usuário durante o processo de cadastro.</p>
 */
public class TelaCadastroCliente extends JFrame {
    
    private Gerenciador gerenciador;
    private JFrame telaAnterior;
    private JTextField campId;
    private JTextField campNome;
    private JTextField campEmail;
    private JPasswordField campSenha;
    private JTextField campTelefone;
    private JTextField campEndereco;
    private JTextField campCPF;
    private JLabel labelStatus;

    /**
     * Construtor da tela de cadastro de cliente.
     * 
     * @param gerenciador instância do gerenciador principal do sistema.
     * @param telaAnterior referência à tela anterior para permitir retorno.
     */
    public TelaCadastroCliente(Gerenciador gerenciador, JFrame telaAnterior) {
        this.gerenciador = gerenciador;
        this.telaAnterior = telaAnterior;
        inicializarComponentes();
    }

    /**
     * Inicializa todos os componentes gráficos da interface.
     * Define layout, estilos visuais e ações dos botões.
     */
    private void inicializarComponentes() {
        setTitle("Cadastro de Cliente");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título
        JLabel labelTitulo = new JLabel("Cadastro de Cliente");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitulo.setForeground(new Color(40, 167, 69));
        painelPrincipal.add(labelTitulo);
        painelPrincipal.add(Box.createVerticalStrut(15));

        // Campos
        adicionarCampo(painelPrincipal, "ID:", campId = new JTextField());
        adicionarCampo(painelPrincipal, "Nome:", campNome = new JTextField());
        adicionarCampo(painelPrincipal, "Email:", campEmail = new JTextField());
        adicionarCampo(painelPrincipal, "Senha:", campSenha = new JPasswordField());
        adicionarCampo(painelPrincipal, "Telefone:", campTelefone = new JTextField());
        adicionarCampo(painelPrincipal, "Endereço:", campEndereco = new JTextField());
        adicionarCampo(painelPrincipal, "CPF:", campCPF = new JTextField());

        painelPrincipal.add(Box.createVerticalStrut(15));

        // Botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));

        JButton botaoCadastrar = new JButton("CADASTRAR");
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoCadastrar.setBackground(new Color(40, 167, 69));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.addActionListener(e -> cadastrarCliente());

        JButton botaoVoltar = new JButton("VOLTAR");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoVoltar.setBackground(new Color(108, 117, 125));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> {
            this.dispose();
            telaAnterior.setVisible(true);
        });

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(Box.createHorizontalStrut(15));
        painelBotoes.add(botaoVoltar);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(Box.createVerticalStrut(15));

        labelStatus = new JLabel("");
        labelStatus.setFont(new Font("Arial", Font.ITALIC, 11));
        labelStatus.setForeground(new Color(40, 167, 69));
        labelStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelStatus);

        setContentPane(painelPrincipal);
    }

    /**
     * Adiciona um campo de entrada com rótulo ao painel principal.
     * 
     * @param painel painel onde o campo será adicionado.
     * @param label texto do rótulo que identifica o campo.
     * @param campo componente de entrada de texto.
     */
    private void adicionarCampo(JPanel painel, String label, JTextField campo) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        painel.add(jLabel);

        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 11));
        painel.add(campo);
        painel.add(Box.createVerticalStrut(10));
    }

    /**
     * Realiza o cadastro de um novo cliente no sistema.
     * <p>Valida os campos obrigatórios, cria o objeto {@link Cliente}
     * e solicita ao {@link Gerenciador} o registro do novo usuário.</p>
     * 
     * <p>Exibe mensagens de sucesso ou erro diretamente na interface.</p>
     */
    private void cadastrarCliente() {
        String id = campId.getText().trim();
        String nome = campNome.getText().trim();
        String email = campEmail.getText().trim();
        String senha = new String(campSenha.getPassword());
        String telefone = campTelefone.getText().trim();
        String endereco = campEndereco.getText().trim();
        String cpf = campCPF.getText().trim();

        if (id.isEmpty() || nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cpf.isEmpty()) {
            labelStatus.setText("Preencha todos os campos!");
            labelStatus.setForeground(new Color(220, 53, 69));
            return;
        }

        Cliente cliente = new Cliente(id, nome, email, senha, telefone, endereco, cpf);

        if (gerenciador.cadastrarCliente(cliente)) {
            labelStatus.setText("Cliente cadastrado com sucesso!");
            labelStatus.setForeground(new Color(40, 167, 69));

            Timer timer = new Timer(2000, e -> {
                this.dispose();
                telaAnterior.setVisible(true);
            });
            timer.setRepeats(false);
            timer.start();
        } else {
            labelStatus.setText("Erro: ID ou Email já existente!");
            labelStatus.setForeground(new Color(220, 53, 69));
        }
    }
}
