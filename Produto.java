package main;

import java.io.Serializable;

/**
 * Representa um produto disponível em um restaurante no sistema de delivery.
 * 
 * <p>Contém informações como nome, preço, descrição, categoria e estado de disponibilidade.
 * Implementa {@link Serializable} para permitir persistência em arquivos.</p>
 */
public class Produto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** Identificador único do produto. */
    private String id;

    /** Nome do produto. */
    private String nome;

    /** Descrição detalhada do produto. */
    private String descricao;

    /** Categoria do produto (ex: Lanche, Bebida, Sobremesa). */
    private String categoria;

    /** Identificador do restaurante ao qual o produto pertence. */
    private String idRestaurante;

    /** Preço do produto. */
    private double preco;

    /** Indica se o produto está ativo no cardápio. */
    private boolean ativo;

    /**
     * Construtor da classe Produto.
     * 
     * @param id identificador único
     * @param nome nome do produto
     * @param descricao descrição do produto
     * @param preco preço do produto
     * @param categoria categoria do produto
     * @param idRestaurante ID do restaurante proprietário
     */
    public Produto(String id, String nome, String descricao, double preco, String categoria, String idRestaurante) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.categoria = categoria;
        this.idRestaurante = idRestaurante;
        this.ativo = true;
    }

    /**
     * Valida se o produto contém informações mínimas válidas.
     * 
     * @return true se o produto for válido
     */
    public boolean validar() {
        return preco > 0 && !nome.isEmpty() && id != null;
    }

    /**
     * Retorna uma representação formatada para exibição em menus.
     * 
     * @return string com ID, nome, preço e categoria do produto
     */
    public String toMenuString() {
        return String.format("[%s] %s - R$ %.2f - %s", id, nome, preco, categoria);
    }

    // ====== GETTERS ======
    public String getId() { return id; }
    public String getNome() { return nome; }
    public String getDescricao() { return descricao; }
    public double getPreco() { return preco; }
    public String getCategoria() { return categoria; }
    public boolean isAtivo() { return ativo; }
    public String getIdRestaurante() { return idRestaurante; }

    // ====== SETTERS ======
    public void setNome(String nome) { this.nome = nome; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public void setPreco(double preco) { if (preco > 0) this.preco = preco; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    /**
     * Retorna uma representação textual completa do produto.
     * 
     * @return string contendo os principais atributos do produto
     */
    @Override
    public String toString() {
        return "Produto{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", preco=" + String.format("R$ %.2f", preco) +
                ", categoria='" + categoria + '\'' +
                ", ativo=" + ativo +
                '}';
    }
}
