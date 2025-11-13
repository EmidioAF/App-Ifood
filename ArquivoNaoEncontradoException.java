package main;

/**
 * Exceção personalizada usada para indicar que um arquivo necessário
 * para o funcionamento do sistema não foi encontrado.
 * 
 * <p>Esta exceção herda de {@link PedidoException}, permitindo tratar erros
 * relacionados a operações de pedidos que envolvem leitura ou gravação de arquivos.</p>
 */
public class ArquivoNaoEncontradoException extends PedidoException {

    /**
     * Cria uma nova exceção do tipo ArquivoNaoEncontradoException com uma mensagem detalhada.
     *
     * @param mensagem descrição adicional do erro que será exibida junto à mensagem padrão.
     */
    public ArquivoNaoEncontradoException(String mensagem) {
        super("Erro de Arquivo: " + mensagem);
    }
}
