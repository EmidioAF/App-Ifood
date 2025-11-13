package main;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/** 
 * Tela responsável por gerenciar o cardápio do restaurante.
 * Permite visualizar, adicionar e remover produtos cadastrados.
 */
public class TelaGerenciarCardapio extends JFrame {
    
    private Gerenciador gerenciador;
    private Restaurante restaurante;
    private JFrame telaPrincipal;
    private JList<String> listaProdutos;
    private JLabel labelTotal;
    
    /** 
     * Construtor da tela de gerenciamento de cardápio.
     * 
     * @param gerenciador Gerenciador principal do sistema.
     * @param restaurante Restaurante logado.
     * @param telaPrincipal Tela anterior para possível retorno.
     */
    public TelaGerenciarCardapio(Gerenciador gerenciador, Restaurante restaurante, JFrame telaPrincipal) {
        this.gerenciador = gerenciador;
        this.restaurante = restaurante;
        this.telaPrincipal = telaPrincipal;
        inicializarComponentes();
    }
    
    /** 
     * Inicializa e configura todos os componentes da interface gráfica.
     */
    private void inicializarComponentes() {
        setTitle("Gerenciar Cardápio");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(650, 500);
        setLocationRelativeTo(null);
        setResizable(false);
        
        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setBackground(new Color(240, 240, 240));
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        /** Criação do título da tela */
        JLabel labelTitulo = new JLabel("Cardápio de " + restaurante.getNome());
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        painelPrincipal.add(labelTitulo);
        painelPrincipal.add(Box.createVerticalStrut(15));
        
        /** Lista de produtos do cardápio */
        listaProdutos = new JList<>();
        listaProdutos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollProdutos = new JScrollPane(listaProdutos);
        scrollProdutos.setPreferredSize(new Dimension(500, 250));
        scrollProdutos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 250));
        painelPrincipal.add(scrollProdutos);
        painelPrincipal.add(Box.createVerticalStrut(10));
        
        /** Exibe total de produtos */
        labelTotal = new JLabel("Total de produtos: 0");
        labelTotal.setFont(new Font("Arial", Font.BOLD, 12));
        painelPrincipal.add(labelTotal);
        painelPrincipal.add(Box.createVerticalStrut(15));
        
        /** Painel de botões (Adicionar, Remover, Voltar) */
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(240, 240, 240));
        painelBotoes.setLayout(new BoxLayout(painelBotoes, BoxLayout.X_AXIS));
        painelBotoes.setMaximumSize(new Dimension(Integer.MAX_VALUE, 50));
        
        JButton botaoAdicionar = new JButton("ADICIONAR PRODUTO");
        botaoAdicionar.setBackground(new Color(40, 167, 69));
        botaoAdicionar.setForeground(Color.WHITE);
        botaoAdicionar.setFocusPainted(false);
        botaoAdicionar.addActionListener(e -> abrirTelaAdicionarProduto());
        
        JButton botaoRemover = new JButton("REMOVER PRODUTO");
        botaoRemover.setBackground(new Color(220, 53, 69));
        botaoRemover.setForeground(Color.WHITE);
        botaoRemover.setFocusPainted(false);
        botaoRemover.addActionListener(e -> removerProduto());
        
        JButton botaoVoltar = new JButton("VOLTAR");
        botaoVoltar.setBackground(new Color(108, 117, 125));
        botaoVoltar.setForeground(Color.WHITE);
        botaoVoltar.setFocusPainted(false);
        botaoVoltar.addActionListener(e -> this.dispose());
        
        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(Box.createHorizontalStrut(10));
        painelBotoes.add(botaoRemover);
        painelBotoes.add(Box.createHorizontalStrut(10));
        painelBotoes.add(botaoVoltar);
        
        painelPrincipal.add(painelBotoes);
        
        atualizarListaProdutos();
        setContentPane(painelPrincipal);
    }
    
    /** 
     * Atualiza a lista de produtos exibida na tela.
     */
    private void atualizarListaProdutos() {
        ArrayList<Produto> produtos = restaurante.obterCardapio();
        String[] descricoes = new String[produtos.size()];
        
        for (int i = 0; i < produtos.size(); i++) {
            descricoes[i] = produtos.get(i).toMenuString();
        }
        
        listaProdutos.setListData(descricoes);
        labelTotal.setText("Total de produtos: " + produtos.size());
    }
    
    /** 
     * Abre uma nova janela para adicionar um novo produto ao cardápio.
     */
    private void abrirTelaAdicionarProduto() {
        JFrame telaAdd = new JFrame("Adicionar Produto");
        telaAdd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        telaAdd.setSize(500, 400);
        telaAdd.setLocationRelativeTo(this);
        telaAdd.setResizable(false);
        
        JPanel painel = new JPanel();
        painel.setBackground(new Color(240, 240, 240));
        painel.setLayout(new BoxLayout(painel, BoxLayout.Y_AXIS));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        adicionarCampoTexto(painel, "ID:", new JTextField());
        JTextField campNome = new JTextField();
        adicionarCampoTexto(painel, "Nome:", campNome);
        
        JTextField campDesc = new JTextField();
        adicionarCampoTexto(painel, "Descrição:", campDesc);
        
        JTextField campPreco = new JTextField();
        adicionarCampoTexto(painel, "Preço:", campPreco);
        
        JTextField campCateg = new JTextField();
        adicionarCampoTexto(painel, "Categoria:", campCateg);
        
        painel.add(Box.createVerticalStrut(15));
        
        JButton botaoSalvar = new JButton("SALVAR");
        botaoSalvar.setBackground(new Color(40, 167, 69));
        botaoSalvar.setForeground(Color.WHITE);
        botaoSalvar.setFocusPainted(false);
        botaoSalvar.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        /** Ação do botão para validar e salvar o novo produto */
        JTextField campId = (JTextField) painel.getComponent(1);
        botaoSalvar.addActionListener(e -> {
            try {
                String id = campId.getText().trim();
                String nome = campNome.getText().trim();
                String desc = campDesc.getText().trim();
                double preco = Double.parseDouble(campPreco.getText().trim());
                String categ = campCateg.getText().trim();
                
                if (id.isEmpty() || nome.isEmpty() || categ.isEmpty()) {
                    JOptionPane.showMessageDialog(telaAdd, "Preencha todos os campos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Produto produto = new Produto(id, nome, desc, preco, categ, restaurante.getId());
                restaurante.adicionarProduto(produto);
                gerenciador.salvarDados();
                atualizarListaProdutos();
                telaAdd.dispose();
                JOptionPane.showMessageDialog(this, "Produto adicionado!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(telaAdd, "Preço inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        painel.add(botaoSalvar);
        telaAdd.setContentPane(painel);
    }
    
    /** 
     * Adiciona um campo de texto com rótulo ao painel.
     * 
     * @param painel Painel onde o campo será adicionado.
     * @param label Texto do rótulo.
     * @param campo Campo de texto.
     */
    private void adicionarCampoTexto(JPanel painel, String label, JTextField campo) {
        JLabel jLabel = new JLabel(label);
        jLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        painel.add(jLabel);
        
        campo.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        painel.add(campo);
        painel.add(Box.createVerticalStrut(10));
    }
    
    /** 
     * Remove o produto selecionado na lista, se existir.
     */
    private void removerProduto() {
        int indice = listaProdutos.getSelectedIndex();
        if (indice >= 0) {
            ArrayList<Produto> produtos = restaurante.obterCardapio();
            if (indice < produtos.size()) {
                Produto p = produtos.get(indice);
                restaurante.removerProduto(p.getId());
                gerenciador.salvarDados();
                atualizarListaProdutos();
                JOptionPane.showMessageDialog(this, "Produto removido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Selecione um produto!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}
