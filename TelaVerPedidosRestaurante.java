package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela responsável por exibir os pedidos recebidos por um restaurante
 * e permitir a atualização do status de cada pedido.
 * 
 * <p>Permite ao usuário selecionar um pedido da lista, visualizar seus detalhes
 * e alterar seu status entre os valores pré-definidos.</p>
 */
public class TelaVerPedidosRestaurante extends JFrame {
    
    /** Gerenciador do sistema, responsável por autenticação e persistência. */
    private Gerenciador gerenciador;
    
    /** Restaurante logado que está visualizando os pedidos. */
    private Restaurante restaurante;
    
    /** Tela anterior, usada ao retornar. */
    private JFrame telaPrincipal;
    
    /** Lista visual que exibe os pedidos recebidos. */
    private JList<String> listaPedidos;
    
    /** Área de texto que exibe os detalhes do pedido selecionado. */
    private JTextArea areaDetalhes;
    
    /** Combo box que permite alterar o status do pedido selecionado. */
    private JComboBox<String> comboStatus;
    
    /**
     * Construtor da tela de visualização de pedidos do restaurante.
     * 
     * @param gerenciador instância do gerenciador do sistema
     * @param restaurante restaurante autenticado
     * @param telaPrincipal referência da tela anterior
     */
    public TelaVerPedidosRestaurante(Gerenciador gerenciador, Restaurante restaurante, JFrame telaPrincipal) {
        this.gerenciador = gerenciador;
        this.restaurante = restaurante;
        this.telaPrincipal = telaPrincipal;
        inicializarComponentes();
    }
    
    /**
     * Inicializa e configura todos os componentes gráficos da tela.
     * Inclui lista de pedidos, área de detalhes, combo de status e botões.
     */
    private void inicializarComponentes() {
        setTitle("Pedidos Recebidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 550);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // --- Painel Esquerdo: Lista de pedidos ---
        JLabel labelPedidos = new JLabel("Pedidos Recebidos:");
        labelPedidos.setFont(new Font("Arial", Font.BOLD, 12));
        
        listaPedidos = new JList<>();
        listaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPedidos.addListSelectionListener(e -> exibirDetalhesPedido());
        
        JScrollPane scrollPedidos = new JScrollPane(listaPedidos);
        scrollPedidos.setPreferredSize(new Dimension(250, 400));
        
        JPanel painelEsquerda = new JPanel();
        painelEsquerda.setLayout(new BoxLayout(painelEsquerda, BoxLayout.Y_AXIS));
        painelEsquerda.add(labelPedidos);
        painelEsquerda.add(scrollPedidos);
        
        // --- Painel Direito: Detalhes e controles ---
        JLabel labelDetalhes = new JLabel("Detalhes:");
        labelDetalhes.setFont(new Font("Arial", Font.BOLD, 12));
        
        areaDetalhes = new JTextArea();
        areaDetalhes.setEditable(false);
        areaDetalhes.setFont(new Font("Courier", Font.PLAIN, 10));
        JScrollPane scrollDetalhes = new JScrollPane(areaDetalhes);
        
        JLabel labelStatus = new JLabel("Alterar Status:");
        labelStatus.setFont(new Font("Arial", Font.PLAIN, 11));
        
        String[] statuses = {"PENDENTE", "CONFIRMADO", "PREPARANDO", "PRONTO", "ENTREGUE", "CANCELADO"};
        comboStatus = new JComboBox<>(statuses);
        comboStatus.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        
        JButton botaoAtualizar = new JButton("ATUALIZAR STATUS");
        botaoAtualizar.setBackground(new Color(0, 123, 255));
        botaoAtualizar.setForeground(Color.WHITE);
        botaoAtualizar.setFocusPainted(false);
        botaoAtualizar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        botaoAtualizar.addActionListener(e -> atualizarStatusPedido());
        
        JPanel painelControles = new JPanel();
        painelControles.setBackground(new Color(240, 240, 240));
        painelControles.setLayout(new BoxLayout(painelControles, BoxLayout.Y_AXIS));
        painelControles.add(labelStatus);
        painelControles.add(comboStatus);
        painelControles.add(Box.createVerticalStrut(10));
        painelControles.add(botaoAtualizar);
        
        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BorderLayout(10, 10));
        painelDireita.add(labelDetalhes, BorderLayout.NORTH);
        painelDireita.add(scrollDetalhes, BorderLayout.CENTER);
        painelDireita.add(painelControles, BorderLayout.SOUTH);
        
        // --- Split pane ---
        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, painelEsquerda, painelDireita);
        split.setDividerLocation(250);
        painelPrincipal.add(split, BorderLayout.CENTER);
        
        // --- Botão Voltar ---
        JButton botaoVoltar = new JButton("VOLTAR");
        botaoVoltar.setBackground(new Color(108, 117, 125));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> this.dispose());
        painelPrincipal.add(botaoVoltar, BorderLayout.SOUTH);
        
        atualizarListaPedidos();
        setContentPane(painelPrincipal);
    }
    
    /**
     * Atualiza a lista de pedidos exibidos no JList.
     * Obtém os pedidos do restaurante e exibe ID e status de cada um.
     */
    private void atualizarListaPedidos() {
        ArrayList<Pedido> pedidos = restaurante.obterPedidosRecebidos();
        String[] descricoes = new String[pedidos.size()];
        
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            descricoes[i] = p.getId() + " - " + p.getStatus().getDescricao();
        }
        
        listaPedidos.setListData(descricoes);
    }
    
    /**
     * Exibe detalhes do pedido selecionado na área de texto
     * e atualiza o combo box com o status atual.
     */
    private void exibirDetalhesPedido() {
        int indice = listaPedidos.getSelectedIndex();
        if (indice >= 0) {
            ArrayList<Pedido> pedidos = restaurante.obterPedidosRecebidos();
            if (indice < pedidos.size()) {
                Pedido pedido = pedidos.get(indice);
                areaDetalhes.setText(pedido.obterDetalhes());
                comboStatus.setSelectedItem(pedido.getStatus().toString());
            }
        }
    }
    
    /**
     * Atualiza o status do pedido selecionado com base na escolha do combo box.
     * Notifica o usuário em caso de sucesso ou erro.
     */
    private void atualizarStatusPedido() {
        int indice = listaPedidos.getSelectedIndex();
        if (indice < 0) {
            JOptionPane.showMessageDialog(this, "Selecione um pedido!", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        ArrayList<Pedido> pedidos = restaurante.obterPedidosRecebidos();
        if (indice < pedidos.size()) {
            Pedido pedido = pedidos.get(indice);
            String statusSelecionado = (String) comboStatus.getSelectedItem();
            
            try {
                Pedido.StatusPedido novoStatus = Pedido.StatusPedido.valueOf(statusSelecionado);
                gerenciador.atualizarStatusPedido(pedido.getId(), novoStatus);
                atualizarListaPedidos();
                exibirDetalhesPedido();
                JOptionPane.showMessageDialog(this, "Status atualizado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IllegalArgumentException e) {
                JOptionPane.showMessageDialog(this, "Status inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
