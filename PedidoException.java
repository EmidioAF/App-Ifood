package main;

/**
 * Exceção base para erros relacionados a operações de pedidos no sistema.
 * 
 * <p>Serve como classe genérica para representar falhas durante o
 * processamento, criação ou manipulação de pedidos.</p>
 */
public class PedidoException extends Exception {

    /**
     * Cria uma nova exceção de pedido com uma mensagem específica.
     *
     * @param mensagem descrição do erro ocorrido.
     */
    public PedidoException(String mensagem) {
        super(mensagem);
    }

    /**
     * Cria uma nova exceção de pedido com uma mensagem e a causa original.
     *
     * @param mensagem descrição do erro ocorrido.
     * @param causa exceção que originou este erro.
     */
    public PedidoException(String mensagem, Throwable causa) {
        super(mensagem, causa);
    }
}
