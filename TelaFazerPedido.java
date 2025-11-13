package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Tela para criar um novo pedido.
 * Permite selecionar restaurante, escolher produtos e confirmar o pedido.
 * Documentação aplicada em Javadoc para classe e todos os métodos.
 * 
 * @author Sistema
 * @version 1.0
 */
public class TelaFazerPedido extends JFrame {

    private Gerenciador gerenciador;
    private Cliente cliente;
    private JFrame telaPrincipal;
    private Restaurante restauranteSelecionado;
    private ArrayList<Produto> produtosSelecionados;
    private JComboBox<String> comboRestaurantes;
    private JList<String> listaProdutos;
    private JLabel labelTotal;

    /**
     * Construtor da tela de fazer pedido.
     *
     * @param gerenciador  instância do {@link Gerenciador} para operações do sistema
     * @param cliente      cliente autenticado que fará o pedido
     * @param telaPrincipal referência para a tela anterior (para retorno)
     */
    public TelaFazerPedido(Gerenciador gerenciador, Cliente cliente, JFrame telaPrincipal) {
        this.gerenciador = gerenciador;
        this.cliente = cliente;
        this.telaPrincipal = telaPrincipal;
        this.produtosSelecionados = new ArrayList<>();
        inicializarComponentes();
    }

    /**
     * Inicializa todos os componentes da interface gráfica.
     * Configura layout, componentes visuais e associa listeners.
     */
    private void inicializarComponentes() {
        setTitle("Fazer Novo Pedido");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(700, 600);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Seleção de restaurante
        JLabel labelRest = new JLabel("Selecione um restaurante:");
        labelRest.setFont(new Font("Arial", Font.BOLD, 12));
        painelPrincipal.add(labelRest);

        ArrayList<Restaurante> restaurantes = gerenciador.obterRestaurantesAbertos();
        ArrayList<String> nomesRestaurantes = new ArrayList<>();
        for (Restaurante r : restaurantes) {
            nomesRestaurantes.add(r.getNome() + " (" + r.getCategoria() + ")");
        }

        comboRestaurantes = new JComboBox<>(nomesRestaurantes.toArray(new String[0]));
        comboRestaurantes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        comboRestaurantes.addActionListener(e -> atualizarProdutos());
        painelPrincipal.add(comboRestaurantes);
        painelPrincipal.add(Box.createVerticalStrut(15));

        // Lista de produtos
        JLabel labelProd = new JLabel("Produtos disponíveis:");
        labelProd.setFont(new Font("Arial", Font.BOLD, 12));
        painelPrincipal.add(labelProd);

        listaProdutos = new JList<>();
        listaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollProdutos = new JScrollPane(listaProdutos);
        scrollProdutos.setPreferredSize(new Dimension(500, 150));
        scrollProdutos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
        painelPrincipal.add(scrollProdutos);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // Botão adicionar produto
        JButton botaoAddProduto = criarBotao("ADICIONAR PRODUTO AO CARRINHO", new Color(40, 167, 69));
        botaoAddProduto.addActionListener(e -> adicionarProdutoCarrinho());
        painelPrincipal.add(botaoAddProduto);
        painelPrincipal.add(Box.createVerticalStrut(15));

        // Total
        labelTotal = new JLabel("Total: R$ 0,00");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 14));
        labelTotal.setForeground(new Color(220, 53, 69));
        painelPrincipal.add(labelTotal);
        painelPrincipal.add(Box.createVerticalStrut(10));

        // Botões de ação
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));

        JButton botaoConfirmar = criarBotao("CONFIRMAR PEDIDO", new Color(0, 123, 255));
        botaoConfirmar.addActionListener(e -> confirmarPedido());

        JButton botaoCancelar = criarBotao("CANCELAR", new Color(220, 53, 69));
        botaoCancelar.addActionListener(e -> dispose());

        painelBotoes.add(botaoConfirmar);
        painelBotoes.add(Box.createHorizontalStrut(15));
        painelBotoes.add(botaoCancelar);
        painelPrincipal.add(painelBotoes);

        setContentPane(painelPrincipal);

        if (restaurantes.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "Não há restaurantes disponíveis no momento.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        } else {
            atualizarProdutos();
        }
    }

    /**
     * Cria um botão padronizado para a interface.
     *
     * @param texto texto exibido no botão
     * @param cor   cor de fundo do botão
     * @return JButton configurado
     */
    private JButton criarBotao(String texto, Color cor) {
        JButton botao = new JButton(texto);
        botao.setFont(new Font("Arial", Font.BOLD, 12));
        botao.setBackground(cor);
        botao.setForeground(Color.WHITE);
        botao.setFocusPainted(false);
        botao.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return botao;
    }

    /**
     * Atualiza a lista de produtos com base no restaurante selecionado no combo.
     * Obtém o cardápio do restaurante e povo­a a {@link JList} de produtos.
     */
    private void atualizarProdutos() {
        if (comboRestaurantes.getSelectedIndex() >= 0) {
            ArrayList<Restaurante> restaurantes = gerenciador.obterRestaurantesAbertos();
            restauranteSelecionado = restaurantes.get(comboRestaurantes.getSelectedIndex());

            ArrayList<String> nomesProdutos = new ArrayList<>();
            for (Produto p : restauranteSelecionado.obterCardapio()) {
                nomesProdutos.add(p.toMenuString());
            }

            listaProdutos.setListData(nomesProdutos.toArray(new String[0]));
        }
    }

    /**
     * Adiciona o produto atualmente selecionado na lista ao carrinho local.
     * Exibe uma confirmação ao usuário.
     */
    private void adicionarProdutoCarrinho() {
        int indice = listaProdutos.getSelectedIndex();
        if (indice >= 0 && restauranteSelecionado != null) {
            ArrayList<Produto> produtos = restauranteSelecionado.obterCardapio();
            if (indice < produtos.size()) {
                produtosSelecionados.add(produtos.get(indice));
                atualizarTotal();
                JOptionPane.showMessageDialog(this,
                        "Produto adicionado ao carrinho.",
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this,
                    "Selecione um produto antes de adicionar.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Recalcula e atualiza o label que mostra o valor total do pedido.
     * Considera soma dos preços dos produtos selecionados mais a taxa de entrega.
     */
    private void atualizarTotal() {
        double total = 0;
        for (Produto p : produtosSelecionados) {
            total += p.getPreco();
        }
        if (restauranteSelecionado != null) {
            total += restauranteSelecionado.getTaxaEntrega();
        }
        labelTotal.setText(String.format("Total: R$ %.2f (%d produtos)", total, produtosSelecionados.size()));
    }

    /**
     * Valida, cria e confirma o pedido.
     * - Gera um ID único simples para o pedido.
     * - Adiciona produtos ao objeto {@link Pedido}.
     * - Deduz o crédito do cliente e persiste o pedido via {@link Gerenciador}.
     *
     * Mostra mensagens de erro/sucesso conforme o resultado.
     */
    private void confirmarPedido() {
        if (restauranteSelecionado == null) {
            JOptionPane.showMessageDialog(this, "Selecione um restaurante primeiro.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (produtosSelecionados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Adicione produtos ao pedido.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String idPedido = "PED" + System.currentTimeMillis();
            Pedido pedido = new Pedido(idPedido, cliente.getId(), restauranteSelecionado.getId(),
                    restauranteSelecionado.getTaxaEntrega());

            for (Produto p : produtosSelecionados) {
                pedido.adicionarProduto(p);
            }

            if (cliente.deduzirCredito(pedido.getTotal())) {
                gerenciador.criarPedido(pedido);
                JOptionPane.showMessageDialog(this,
                        "Pedido criado com sucesso!\nID: " + idPedido,
                        "Sucesso",
                        JOptionPane.INFORMATION_MESSAGE);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this,
                        "Crédito insuficiente.\nSaldo atual: R$ " + String.format("%.2f", cliente.getCredito()),
                        "Erro",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (PedidoInvalidoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
