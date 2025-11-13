package main;

import java.util.ArrayList;

/**
 * Representa um restaurante dentro do sistema de delivery.
 * 
 * <p>Gerencia cardápio, pedidos recebidos, status de funcionamento e informações gerais do estabelecimento.</p>
 */
public class Restaurante extends Usuario {

    private static final long serialVersionUID = 1L;

    /** Lista de produtos oferecidos no cardápio do restaurante. */
    private ArrayList<Produto> cardapio;

    /** Lista de pedidos recebidos pelo restaurante. */
    private ArrayList<Pedido> pedidosRecebidos;

    /** CNPJ do restaurante. */
    private String cnpj;

    /** Categoria principal do restaurante (ex: Pizzaria, Lanchonete, Japonesa). */
    private String categoria;

    /** Valor da taxa de entrega cobrada pelo restaurante. */
    private double taxaEntrega;

    /** Indica se o restaurante está aberto para pedidos. */
    private boolean aberto;

    /**
     * Construtor da classe Restaurante.
     * 
     * @param id identificador único
     * @param nome nome do restaurante
     * @param email email de login
     * @param senha senha de acesso
     * @param telefone telefone de contato
     * @param endereco endereço completo
     * @param cnpj CNPJ do restaurante
     * @param categoria tipo de culinária ou categoria principal
     * @param taxaEntrega valor da taxa de entrega
     */
    public Restaurante(String id, String nome, String email, String senha,
                      String telefone, String endereco, String cnpj,
                      String categoria, double taxaEntrega) {
        super(id, nome, email, senha, telefone, endereco);
        this.cnpj = cnpj;
        this.categoria = categoria;
        this.taxaEntrega = taxaEntrega;
        this.cardapio = new ArrayList<>();
        this.pedidosRecebidos = new ArrayList<>();
        this.aberto = true;
    }

    /**
     * Adiciona um novo produto ao cardápio do restaurante.
     * 
     * @param produto produto a ser adicionado
     */
    public void adicionarProduto(Produto produto) {
        if (produto != null && !verificarProdutoExistente(produto.getId())) 
            cardapio.add(produto);
    }

    /**
     * Remove um produto do cardápio com base no seu ID.
     * 
     * @param idProduto ID do produto a ser removido
     * @return true se o produto foi removido com sucesso
     */
    public boolean removerProduto(String idProduto) {
        for (Produto p : cardapio) {
            if (p.getId().equals(idProduto)) {
                cardapio.remove(p);
                return true;
            }
        }
        return false;
    }

    /**
     * Verifica se um produto já existe no cardápio.
     * 
     * @param idProduto ID do produto
     * @return true se o produto já existir
     */
    private boolean verificarProdutoExistente(String idProduto) {
        for (Produto p : cardapio) 
            if (p.getId().equals(idProduto)) return true;
        return false;
    }

    /**
     * Busca um produto pelo seu ID no cardápio.
     * 
     * @param idProduto ID do produto
     * @return produto encontrado ou null
     */
    public Produto obterProdutoById(String idProduto) {
        for (Produto p : cardapio) 
            if (p.getId().equals(idProduto)) return p;
        return null;
    }

    /**
     * Retorna uma cópia da lista de produtos do cardápio.
     * 
     * @return lista de produtos
     */
    public ArrayList<Produto> obterCardapio() { 
        return new ArrayList<>(cardapio); 
    }

    /**
     * Adiciona um pedido recebido à lista de pedidos do restaurante.
     * 
     * @param pedido pedido recebido
     */
    public void receberPedido(Pedido pedido) {
        if (pedido != null) pedidosRecebidos.add(pedido);
    }

    /**
     * Retorna todos os pedidos recebidos pelo restaurante.
     * 
     * @return lista de pedidos
     */
    public ArrayList<Pedido> obterPedidosRecebidos() { 
        return new ArrayList<>(pedidosRecebidos); 
    }

    /**
     * Exibe o menu principal do restaurante no console.
     */
    public void exibirMenu() {
        System.out.println("=== Menu Restaurante ===\n1. Gerenciar cardápio\n2. Ver pedidos\n3. Atualizar status\n4. Abrir/Fechar\n5. Sair");
    }

    /**
     * Retorna o tipo do usuário.
     * 
     * @return "Restaurante"
     */
    public String getTipoUsuario() { return "Restaurante"; }

    // ====== GETTERS ======
    public String getCnpj() { return cnpj; }
    public String getCategoria() { return categoria; }
    public double getTaxaEntrega() { return taxaEntrega; }
    public boolean isAberto() { return aberto; }
    public int getQuantidadeProdutos() { return cardapio.size(); }
    public int getQuantidadePedidos() { return pedidosRecebidos.size(); }

    // ====== SETTERS ======
    public void setAberto(boolean aberto) { this.aberto = aberto; }
    public void setTaxaEntrega(double taxaEntrega) { if (taxaEntrega >= 0) this.taxaEntrega = taxaEntrega; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    /**
     * Retorna uma representação textual do restaurante.
     * 
     * @return string contendo os principais dados do restaurante
     */
    @Override
    public String toString() {
        return "Restaurante{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cnpj='" + cnpj + '\'' +
                ", categoria='" + categoria + '\'' +
                ", taxaEntrega=" + taxaEntrega +
                ", produtos=" + cardapio.size() +
                ", aberto=" + aberto +
                '}';
    }
}
