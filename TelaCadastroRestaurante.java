package main;

import javax.swing.*;
import java.awt.*;

/**
 * Tela de Cadastro de Restaurante.
 * 
 * <p>Permite a criação de uma nova conta de restaurante no sistema.
 * Inclui campos de identificação, contato e dados comerciais
 * como CNPJ, categoria e taxa de entrega.</p>
 * 
 * <p>Realiza validação dos dados e exibe mensagens de sucesso ou erro
 * diretamente na interface.</p>
 */
public class TelaCadastroRestaurante extends JFrame {

    private Gerenciador gerenciador;
    private JFrame telaAnterior;
    private JTextField campId;
    private JTextField campNome;
    private JTextField campEmail;
    private JPasswordField campSenha;
    private JTextField campTelefone;
    private JTextField campEndereco;
    private JTextField campCNPJ;
    private JTextField campCategoria;
    private JTextField campTaxaEntrega;
    private JLabel labelStatus;

    /**
     * Construtor da tela de cadastro de restaurante.
     * 
     * @param gerenciador    instância do gerenciador principal do sistema
     * @param telaAnterior   referência à tela anterior para retorno
     */
    public TelaCadastroRestaurante(Gerenciador gerenciador, JFrame telaAnterior) {
        this.gerenciador = gerenciador;
        this.telaAnterior = telaAnterior;
        inicializarComponentes();
    }

    /**
     * Inicializa todos os componentes gráficos da interface.
     * Configura layout, cores, botões e eventos.
     */
    private void inicializarComponentes() {
        setTitle("Cadastro de Restaurante");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 550);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Título
        JLabel labelTitulo = new JLabel("Cadastro de Restaurante");
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        labelTitulo.setForeground(new Color(220, 53, 69));
        painelPrincipal.add(labelTitulo);
        painelPrincipal.add(Box.createVerticalStrut(15));

        // Campos de entrada
        adicionarCampo(painelPrincipal, "ID:", campId = new JTextField());
        adicionarCampo(painelPrincipal, "Nome:", campNome = new JTextField());
        adicionarCampo(painelPrincipal, "Email:", campEmail = new JTextField());
        adicionarCampo(painelPrincipal, "Senha:", campSenha = new JPasswordField());
        adicionarCampo(painelPrincipal, "Telefone:", campTelefone = new JTextField());
        adicionarCampo(painelPrincipal, "Endereço:", campEndereco = new JTextField());
        adicionarCampo(painelPrincipal, "CNPJ:", campCNPJ = new JTextField());
        adicionarCampo(painelPrincipal, "Categoria:", campCategoria = new JTextField());
        adicionarCampo(painelPrincipal, "Taxa de Entrega (R$):", campTaxaEntrega = new JTextField());

        painelPrincipal.add(Box.createVerticalStrut(15));

        // Painel de botões
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));

        JButton botaoCadastrar = new JButton("CADASTRAR");
        botaoCadastrar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoCadastrar.setBackground(new Color(220, 53, 69));
        botaoCadastrar.setForeground(Color.WHITE);
        botaoCadastrar.setFocusPainted(false);
        botaoCadastrar.addActionListener(e -> cadastrarRestaurante());

        JButton botaoVoltar = new JButton("VOLTAR");
        botaoVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        botaoVoltar.setBackground(new Color(108, 117, 125));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> {
            dispose();
            telaAnterior.setVisible(true);
        });

        painelBotoes.add(botaoCadastrar);
        painelBotoes.add(Box.createHorizontalStrut(15));
        painelBotoes.add(botaoVoltar);
        painelPrincipal.add(painelBotoes);
        painelPrincipal.add(Box.createVerticalStrut(15));

        // Label de status
        labelStatus = new JLabel("");
        labelStatus.setFont(new Font("Arial", Font.ITALIC, 11));
        labelStatus.setForeground(new Color(220, 53, 69));
        labelStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelStatus);

        setContentPane(painelPrincipal);
    }

    /**
     * Adiciona um campo de entrada com rótulo ao painel principal.
     * 
     * @param painel painel onde o campo será adicionado
     * @param label  texto do rótulo
     * @param campo  componente de entrada (campo de texto)
     */
    private void adicionarCampo(JPanel painel, String label, JTextField campo) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        painel.add(jLabel);

        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        campo.setFont(new Font("Arial", Font.PLAIN, 11));
        painel.add(campo);
        painel.add(Box.createVerticalStrut(8));
    }

    /**
     * Realiza o cadastro de um novo restaurante.
     * 
     * <p>Valida campos obrigatórios e verifica a taxa de entrega.
     * Caso tudo esteja correto, cria o objeto {@link Restaurante} e
     * solicita ao {@link Gerenciador} o registro.</p>
     * 
     * <p>Exibe mensagens de feedback conforme o resultado.</p>
     */
    private void cadastrarRestaurante() {
        String id = campId.getText().trim();
        String nome = campNome.getText().trim();
        String email = campEmail.getText().trim();
        String senha = new String(campSenha.getPassword());
        String telefone = campTelefone.getText().trim();
        String endereco = campEndereco.getText().trim();
        String cnpj = campCNPJ.getText().trim();
        String categoria = campCategoria.getText().trim();
        String taxaStr = campTaxaEntrega.getText().trim();

        if (id.isEmpty() || nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cnpj.isEmpty()) {
            exibirMensagem("Preencha todos os campos!", new Color(220, 53, 69));
            return;
        }

        try {
            double taxa = Double.parseDouble(taxaStr);
            if (taxa < 0) {
                exibirMensagem("Taxa de entrega não pode ser negativa!", new Color(220, 53, 69));
                return;
            }

            Restaurante restaurante = new Restaurante(id, nome, email, senha,
                    telefone, endereco, cnpj, categoria, taxa);

            if (gerenciador.cadastrarRestaurante(restaurante)) {
                exibirMensagem("Restaurante cadastrado com sucesso!", new Color(40, 167, 69));
                Timer timer = new Timer(2000, e -> {
                    dispose();
                    telaAnterior.setVisible(true);
                });
                timer.setRepeats(false);
                timer.start();
            } else {
                exibirMensagem("Erro: ID ou Email já existente!", new Color(220, 53, 69));
            }
        } catch (NumberFormatException e) {
            exibirMensagem("Taxa de entrega inválida!", new Color(220, 53, 69));
        }
    }

    /**
     * Exibe uma mensagem no label de status com a cor especificada.
     * 
     * @param mensagem texto a ser exibido
     * @param cor      cor do texto
     */
    private void exibirMensagem(String mensagem, Color cor) {
        labelStatus.setText(mensagem);
        labelStatus.setForeground(cor);
    }
}
