package main;

/**
 * Exceção lançada quando um usuário falha na autenticação.
 * <p>
 * Extende PedidoException para manter consistência no tratamento de exceções do sistema.
 * </p>
 */
public class UsuarioNaoAutenticadoException extends PedidoException {

    /**
     * Construtor que recebe a mensagem de erro detalhada.
     *
     * @param mensagem Detalhe sobre a falha de autenticação
     */
    public UsuarioNaoAutenticadoException(String mensagem) {
        super("Autenticação Falhou: " + mensagem);
    }
}
