package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Representa um pedido realizado no sistema de delivery.
 * Contém informações sobre cliente, restaurante, produtos,
 * valores e status do pedido.
 */
public class Pedido implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /**
     * Enum que representa os possíveis status de um pedido.
     */
    public enum StatusPedido {
        Pendente("Pendente"),
        Confirmado("Confirmado"),
        Preparando("Preparando"),
        Pronto("Pronto"),
        Entregue("Entregue"),
        Cancelado("Cancelado");
        
        private final String descricao;

        StatusPedido(String descricao) {
            this.descricao = descricao;
        }

        /**
         * Obtém a descrição legível do status.
         * @return descrição do status
         */
        public String getDescricao() {
            return descricao;
        }
    }
    
    private String id;
    private String idCliente;
    private String idRestaurante;
    private String observacoes;
    private ArrayList<Produto> produtos;
    private StatusPedido status;
    private LocalDateTime dataPedido;
    private double total;
    private double taxaEntrega;

    /**
     * Construtor da classe Pedido.
     * 
     * @param id identificador único do pedido
     * @param idCliente identificador do cliente
     * @param idRestaurante identificador do restaurante
     * @param taxaEntrega valor da taxa de entrega
     */
    public Pedido(String id, String idCliente, String idRestaurante, double taxaEntrega) {
        this.id = id;
        this.idCliente = idCliente;
        this.idRestaurante = idRestaurante;
        this.produtos = new ArrayList<>();
        this.status = StatusPedido.Pendente;
        this.dataPedido = LocalDateTime.now();
        this.total = 0.0;
        this.observacoes = "";
        this.taxaEntrega = taxaEntrega;
    }

    /**
     * Adiciona um produto ao pedido, caso ele esteja ativo.
     * @param produto produto a ser adicionado
     */
    public void adicionarProduto(Produto produto) {
        if (produto != null && produto.isAtivo()) {
            produtos.add(produto);
            recalcularTotal();
        }
    }

    /**
     * Remove um produto da lista de produtos do pedido.
     * 
     * @param indice posição do produto na lista
     * @return true se o produto foi removido, false caso contrário
     */
    public boolean removerProduto(int indice) {
        if (indice >= 0 && indice < produtos.size()) {
            produtos.remove(indice);
            recalcularTotal();
            return true;
        }
        return false;
    }

    /**
     * Recalcula o valor total do pedido (produtos + taxa de entrega).
     */
    private void recalcularTotal() {
        total = taxaEntrega;
        for (Produto p : produtos) {
            total += p.getPreco();
        }
    }

    /**
     * Retorna uma cópia da lista de produtos do pedido.
     * @return lista de produtos
     */
    public ArrayList<Produto> obterProdutos() {
        return new ArrayList<>(produtos);
    }

    /**
     * Altera o status atual do pedido.
     * @param novoStatus novo status a ser atribuído
     */
    public void alterarStatus(StatusPedido novoStatus) {
        this.status = novoStatus;
    }

    /**
     * Valida se o pedido é considerado válido para processamento.
     * @return true se contém produtos e valor total maior que zero
     */
    public boolean validar() {
        return !produtos.isEmpty() && total > 0;
    }

    /**
     * Obtém o subtotal (soma dos preços dos produtos, sem taxa de entrega).
     * @return valor subtotal do pedido
     */
    public double obterSubtotal() {
        double subtotal = 0.0;
        for (Produto p : produtos) {
            subtotal += p.getPreco();
        }
        return subtotal;
    }

    /**
     * Retorna uma string formatada com os detalhes do pedido.
     * Inclui produtos, valores e status.
     * 
     * @return detalhes formatados do pedido
     */
    public String obterDetalhes() {
        StringBuilder sb = new StringBuilder();
        sb.append("\n=== Detalhes do Pedido ===\nID: ").append(id)
          .append("\nStatus: ").append(status.getDescricao())
          .append("\nData: ").append(dataPedido.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")))
          .append("\n\nProdutos:\n");
        
        for (int i = 0; i < produtos.size(); i++) {
            sb.append(String.format("  %d. %s - R$ %.2f\n", i + 1, produtos.get(i).getNome(), produtos.get(i).getPreco()));
        }
        
        sb.append(String.format(
            "\nSubtotal: R$ %.2f\nTaxa: R$ %.2f\nTOTAL: R$ %.2f\n",
            obterSubtotal(), taxaEntrega, total
        ));
        
        if (!observacoes.isEmpty()) {
            sb.append("Obs: ").append(observacoes).append("\n");
        }
        
        return sb.toString();
    }

    // ========================= Getters e Setters ========================= //

    /** @return identificador do pedido */
    public String getId() { return id; }

    /** @return identificador do cliente */
    public String getIdCliente() { return idCliente; }

    /** @return identificador do restaurante */
    public String getIdRestaurante() { return idRestaurante; }

    /** @return status atual do pedido */
    public StatusPedido getStatus() { return status; }

    /** @return data e hora de criação do pedido */
    public LocalDateTime getDataPedido() { return dataPedido; }

    /** @return valor total do pedido */
    public double getTotal() { return total; }

    /** @return observações adicionais do pedido */
    public String getObservacoes() { return observacoes; }

    /** @return valor da taxa de entrega */
    public double getTaxaEntrega() { return taxaEntrega; }

    /** @return quantidade total de produtos no pedido */
    public int getQuantidadeProdutos() { return produtos.size(); }

    /**
     * Define observações adicionais para o pedido.
     * @param observacoes texto com observações
     */
    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }
}
