package main;

import java.util.ArrayList;

/**
 * Representa um cliente do sistema de delivery.
 * 
 * <p>Um cliente é um tipo de {@link Usuario} que possui CPF, saldo de crédito,
 * lista de pedidos realizados e status de atividade. Ele pode adicionar crédito,
 * realizar pedidos e consultar seu histórico.</p>
 */
public class Cliente extends Usuario {
    private static final long serialVersionUID = 1L;

    /** Lista de pedidos realizados pelo cliente. */
    private ArrayList<Pedido> pedidos;

    /** Valor total de crédito disponível na conta do cliente. */
    private double credito;

    /** CPF do cliente. */
    private String cpf;

    /** Indica se o cliente está ativo no sistema. */
    private boolean ativo;

    /**
     * Construtor da classe Cliente.
     *
     * @param id identificador único do cliente.
     * @param nome nome completo do cliente.
     * @param email email cadastrado.
     * @param senha senha de acesso.
     * @param telefone número de telefone do cliente.
     * @param endereco endereço completo do cliente.
     * @param cpf número de CPF do cliente.
     */
    public Cliente(String id, String nome, String email, String senha,
                   String telefone, String endereco, String cpf) {
        super(id, nome, email, senha, telefone, endereco);
        this.cpf = cpf;
        this.pedidos = new ArrayList<>();
        this.credito = 0.0;
        this.ativo = true;
    }

    /**
     * Adiciona crédito à conta do cliente.
     *
     * @param valor valor positivo a ser adicionado ao saldo.
     */
    public void adicionarCredito(double valor) {
        if (valor > 0) this.credito += valor;
    }

    /**
     * Deduz um valor do crédito do cliente, caso haja saldo suficiente.
     *
     * @param valor valor a ser deduzido.
     * @return {@code true} se o valor foi deduzido com sucesso; {@code false} caso contrário.
     */
    public boolean deduzirCredito(double valor) {
        if (valor > 0 && this.credito >= valor) {
            this.credito -= valor;
            return true;
        }
        return false;
    }

    /**
     * Adiciona um pedido à lista de pedidos do cliente.
     *
     * @param pedido objeto {@link Pedido} a ser adicionado.
     */
    public void adicionarPedido(Pedido pedido) {
        if (pedido != null) pedidos.add(pedido);
    }

    /**
     * Retorna uma cópia da lista de pedidos realizados pelo cliente.
     *
     * @return uma nova lista contendo os pedidos do cliente.
     */
    public ArrayList<Pedido> obterPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Busca um pedido específico com base no seu identificador.
     *
     * @param idPedido identificador único do pedido.
     * @return o objeto {@link Pedido} correspondente ou {@code null} se não for encontrado.
     */
    public Pedido obterPedidoById(String idPedido) {
        for (Pedido p : pedidos) {
            if (p.getId().equals(idPedido)) return p;
        }
        return null;
    }

    /**
     * Exibe o menu de opções disponíveis para o cliente.
     * Esse método apenas imprime o menu no console.
     */
    public void exibirMenu() {
        System.out.println("=== Menu Cliente ===\n1. Fazer novo pedido\n2. Ver meus pedidos\n3. Adicionar crédito\n4. Ver saldo\n5. Sair");
    }

    /**
     * Retorna o tipo de usuário.
     *
     * @return uma string representando o tipo de usuário ("Cliente").
     */
    public String getTipoUsuario() { return "Cliente"; }

    /**
     * Obtém o saldo de crédito atual do cliente.
     *
     * @return valor atual de crédito.
     */
    public double getCredito() { return credito; }

    /**
     * Retorna o CPF do cliente.
     *
     * @return número de CPF.
     */
    public String getCpf() { return cpf; }

    /**
     * Verifica se o cliente está ativo no sistema.
     *
     * @return {@code true} se o cliente estiver ativo; {@code false} caso contrário.
     */
    public boolean isAtivo() { return ativo; }

    /**
     * Retorna a quantidade total de pedidos feitos pelo cliente.
     *
     * @return número de pedidos.
     */
    public int getQuantidadePedidos() { return pedidos.size(); }

    /**
     * Define o status de atividade do cliente.
     *
     * @param ativo {@code true} para ativar, {@code false} para desativar.
     */
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    /**
     * Altera o CPF do cliente.
     *
     * @param cpf novo número de CPF.
     */
    public void setCpf(String cpf) { this.cpf = cpf; }

    /**
     * Retorna uma representação em texto do cliente, incluindo seus principais dados.
     *
     * @return string com as informações resumidas do cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", credito=" + credito +
                ", pedidos=" + pedidos.size() +
                ", ativo=" + ativo +
                '}';
    }
}
