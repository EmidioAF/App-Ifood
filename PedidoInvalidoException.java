package main;

/**
 * Exceção lançada quando um pedido é considerado inválido.
 * 
 * <p>Usada para indicar problemas durante a criação ou validação de um pedido,
 * como ausência de produtos, valores incorretos ou dados inconsistentes.</p>
 */
public class PedidoInvalidoException extends PedidoException {

    /**
     * Cria uma nova exceção indicando que o pedido é inválido.
     *
     * @param mensagem descrição detalhada do motivo da invalidez.
     */
    public PedidoInvalidoException(String mensagem) {
        super("Pedido Inválido: " + mensagem);
    }
}
