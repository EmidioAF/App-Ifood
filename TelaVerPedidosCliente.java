package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela responsável por exibir o histórico de pedidos de um cliente.
 * 
 * <p>Permite que o usuário visualize uma lista de pedidos realizados,
 * consulte os detalhes de cada pedido e retorne à tela anterior.</p>
 */
public class TelaVerPedidosCliente extends JFrame {
    
    /** Cliente logado que está visualizando os pedidos. */
    private Cliente cliente;
    
    /** Referência para a tela principal, usada ao retornar. */
    private JFrame telaPrincipal;
    
    /** Lista visual que exibe os pedidos do cliente. */
    private JList<String> listaPedidos;
    
    /** Área de texto que mostra os detalhes do pedido selecionado. */
    private JTextArea areaDetalhes;
    
    /**
     * Construtor que inicializa a tela de visualização de pedidos do cliente.
     * 
     * @param cliente instância do cliente logado
     * @param telaPrincipal referência da tela anterior
     */
    public TelaVerPedidosCliente(Cliente cliente, JFrame telaPrincipal) {
        this.cliente = cliente;
        this.telaPrincipal = telaPrincipal;
        inicializarComponentes();
    }
    
    /**
     * Configura e inicializa todos os componentes da interface gráfica.
     * Define o layout, cores, eventos e popula a lista de pedidos.
     */
    private void inicializarComponentes() {
        setTitle("Meus Pedidos");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // --- Painel Esquerdo: Lista de pedidos ---
        JLabel labelPedidos = new JLabel("Seus Pedidos:");
        labelPedidos.setFont(new Font("Arial", Font.BOLD, 12));
        
        listaPedidos = new JList<>();
        listaPedidos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaPedidos.addListSelectionListener(e -> exibirDetalhesPedido());
        
        JScrollPane scrollPedidos = new JScrollPane(listaPedidos);
        scrollPedidos.setPreferredSize(new Dimension(200, 400));
        
        JPanel painelEsquerda = new JPanel();
        painelEsquerda.setLayout(new BoxLayout(painelEsquerda, BoxLayout.Y_AXIS));
        painelEsquerda.add(labelPedidos);
        painelEsquerda.add(scrollPedidos);
        
        // --- Painel Direito: Detalhes do pedido ---
        JLabel labelDetalhes = new JLabel("Detalhes do Pedido:");
        labelDetalhes.setFont(new Font("Arial", Font.BOLD, 12));
        
        areaDetalhes = new JTextArea();
        areaDetalhes.setEditable(false);
        areaDetalhes.setFont(new Font("Courier", Font.PLAIN, 11));
        
        JScrollPane scrollDetalhes = new JScrollPane(areaDetalhes);
        
        JPanel painelDireita = new JPanel();
        painelDireita.setLayout(new BoxLayout(painelDireita, BoxLayout.Y_AXIS));
        painelDireita.add(labelDetalhes);
        painelDireita.add(scrollDetalhes);
        
        // --- Divisão da tela ---
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
     * Atualiza a lista de pedidos exibida na tela com base nos dados do cliente.
     * 
     * <p>Obtém os pedidos do cliente e preenche a lista com descrições
     * contendo o ID e o status de cada pedido.</p>
     */
    private void atualizarListaPedidos() {
        ArrayList<Pedido> pedidos = cliente.obterPedidos();
        String[] descricoes = new String[pedidos.size()];
        
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido p = pedidos.get(i);
            descricoes[i] = p.getId() + " - " + p.getStatus().getDescricao();
        }
        
        listaPedidos.setListData(descricoes);
    }
    
    /**
     * Exibe os detalhes completos do pedido selecionado pelo usuário.
     * 
     * <p>Ao selecionar um pedido na lista, esta função busca o objeto
     * correspondente e exibe suas informações no painel de detalhes.</p>
     */
    private void exibirDetalhesPedido() {
        int indice = listaPedidos.getSelectedIndex();
        if (indice >= 0) {
            ArrayList<Pedido> pedidos = cliente.obterPedidos();
            if (indice < pedidos.size()) {
                Pedido pedido = pedidos.get(indice);
                areaDetalhes.setText(pedido.obterDetalhes());
            }
        }
    }
}
