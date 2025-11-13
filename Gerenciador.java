package main;

import java.util.ArrayList;

/**
 * Classe responsável por gerenciar as operações principais do sistema,
 * incluindo o controle de clientes, restaurantes, pedidos e autenticação de usuários.
 * Atua como camada de controle central entre as classes de domínio.
 */
public class Gerenciador {

    /** Lista de todos os clientes cadastrados no sistema */
    private ArrayList<Cliente> clientes;

    /** Lista de todos os restaurantes cadastrados no sistema */
    private ArrayList<Restaurante> restaurantes;

    /** Lista de todos os pedidos realizados */
    private ArrayList<Pedido> pedidos;

    /** Usuário atualmente logado no sistema (Cliente ou Restaurante) */
    private Usuario usuarioLogado;

    /**
     * Construtor da classe Gerenciador.
     * Inicializa as listas e tenta carregar os dados salvos em arquivo.
     */
    public Gerenciador() {
        this.clientes = new ArrayList<>();
        this.restaurantes = new ArrayList<>();
        this.pedidos = new ArrayList<>();
        this.usuarioLogado = null;
        carregarDados();
    }

    /**
     * Carrega os dados salvos em arquivos (clientes, restaurantes e pedidos).
     * Caso os arquivos não sejam encontrados, exibe uma mensagem de erro.
     */
    public void carregarDados() {
        try {
            ArquivoUtils.inicializarDiretorio();
            this.clientes = ArquivoUtils.carregarClientes();
            this.restaurantes = ArquivoUtils.carregarRestaurantes();
            this.pedidos = ArquivoUtils.carregarPedidos();
        } catch (ArquivoNaoEncontradoException e) {
            System.err.println("Erro ao carregar: " + e.getMessage());
        }
    }

    /**
     * Salva os dados atuais de clientes, restaurantes e pedidos nos arquivos correspondentes.
     */
    public void salvarDados() {
        try {
            ArquivoUtils.salvarClientes(clientes);
            ArquivoUtils.salvarRestaurantes(restaurantes);
            ArquivoUtils.salvarPedidos(pedidos);
        } catch (ArquivoNaoEncontradoException e) {
            System.err.println("Erro ao salvar: " + e.getMessage());
        }
    }

    /**
     * Cadastra um novo cliente, caso o ID não exista.
     *
     * @param c Cliente a ser cadastrado
     * @return true se o cliente for adicionado com sucesso
     */
    public boolean cadastrarCliente(Cliente c) {
        if (c != null && !existeClienteComId(c.getId())) {
            clientes.add(c);
            salvarDados();
            return true;
        }
        return false;
    }

    /**
     * Autentica um cliente com base em e-mail e senha.
     *
     * @param email Email do cliente
     * @param senha Senha do cliente
     * @return Cliente autenticado
     * @throws UsuarioNaoAutenticadoException Se as credenciais forem inválidas
     */
    public Cliente autenticarCliente(String email, String senha) throws UsuarioNaoAutenticadoException {
        for (Cliente c : clientes) {
            if (c.validarCredenciais(email, senha)) {
                this.usuarioLogado = c;
                return c;
            }
        }
        throw new UsuarioNaoAutenticadoException("Email ou senha inválidos");
    }

    /**
     * Busca um cliente pelo ID.
     *
     * @param id ID do cliente
     * @return Cliente correspondente ou null se não encontrado
     */
    public Cliente obterClienteById(String id) {
        for (Cliente c : clientes)
            if (c.getId().equals(id)) return c;
        return null;
    }

    /**
     * Verifica se já existe um cliente com o ID especificado.
     *
     * @param id ID do cliente
     * @return true se o cliente existir
     */
    private boolean existeClienteComId(String id) {
        return obterClienteById(id) != null;
    }

    /**
     * Retorna todos os clientes ativos.
     *
     * @return Lista de clientes ativos
     */
    public ArrayList<Cliente> obterClientesAtivos() {
        ArrayList<Cliente> ativos = new ArrayList<>();
        for (Cliente c : clientes)
            if (c.isAtivo()) ativos.add(c);
        return ativos;
    }

    /**
     * Cadastra um novo restaurante, se o ID ainda não existir.
     *
     * @param r Restaurante a ser cadastrado
     * @return true se o restaurante for adicionado com sucesso
     */
    public boolean cadastrarRestaurante(Restaurante r) {
        if (r != null && !existeRestauranteComId(r.getId())) {
            restaurantes.add(r);
            salvarDados();
            return true;
        }
        return false;
    }

    /**
     * Autentica um restaurante.
     *
     * @param email Email do restaurante
     * @param senha Senha do restaurante
     * @return Restaurante autenticado
     * @throws UsuarioNaoAutenticadoException Se as credenciais forem inválidas
     */
    public Restaurante autenticarRestaurante(String email, String senha) throws UsuarioNaoAutenticadoException {
        for (Restaurante r : restaurantes) {
            if (r.validarCredenciais(email, senha)) {
                this.usuarioLogado = r;
                return r;
            }
        }
        throw new UsuarioNaoAutenticadoException("Email ou senha inválidos");
    }

    /**
     * Obtém um restaurante pelo ID.
     *
     * @param id ID do restaurante
     * @return Restaurante encontrado ou null
     */
    public Restaurante obterRestauranteById(String id) {
        for (Restaurante r : restaurantes)
            if (r.getId().equals(id)) return r;
        return null;
    }

    /**
     * Verifica se existe restaurante com o ID especificado.
     *
     * @param id ID do restaurante
     * @return true se existir
     */
    private boolean existeRestauranteComId(String id) {
        return obterRestauranteById(id) != null;
    }

    /**
     * Retorna todos os restaurantes que estão abertos.
     *
     * @return Lista de restaurantes abertos
     */
    public ArrayList<Restaurante> obterRestaurantesAbertos() {
        ArrayList<Restaurante> abertos = new ArrayList<>();
        for (Restaurante r : restaurantes)
            if (r.isAberto()) abertos.add(r);
        return abertos;
    }

    /**
     * Retorna todos os restaurantes cadastrados.
     *
     * @return Lista completa de restaurantes
     */
    public ArrayList<Restaurante> obterTodosRestaurantes() {
        return new ArrayList<>(restaurantes);
    }

    /**
     * Cria um novo pedido, validando o cliente, restaurante e crédito disponível.
     *
     * @param p Pedido a ser criado
     * @return true se o pedido for criado com sucesso
     * @throws PedidoInvalidoException Se o pedido for inválido ou houver problema de crédito
     */
    public boolean criarPedido(Pedido p) throws PedidoInvalidoException {
        if (p == null || !p.validar())
            throw new PedidoInvalidoException("Produtos vazios ou total inválido");

        Cliente cliente = obterClienteById(p.getIdCliente());
        Restaurante restaurante = obterRestauranteById(p.getIdRestaurante());

        if (cliente == null || restaurante == null)
            throw new PedidoInvalidoException("Cliente ou restaurante não encontrado");
        if (!cliente.deduzirCredito(p.getTotal()))
            throw new PedidoInvalidoException("Crédito insuficiente");

        cliente.adicionarPedido(p);
        restaurante.receberPedido(p);
        pedidos.add(p);
        salvarDados();
        return true;
    }

    /**
     * Obtém um pedido pelo ID.
     *
     * @param id ID do pedido
     * @return Pedido encontrado ou null
     */
    public Pedido obterPedidoById(String id) {
        for (Pedido p : pedidos)
            if (p.getId().equals(id)) return p;
        return null;
    }

    /**
     * Atualiza o status de um pedido existente.
     *
     * @param id ID do pedido
     * @param status Novo status do pedido
     * @return true se o status for alterado com sucesso
     */
    public boolean atualizarStatusPedido(String id, Pedido.StatusPedido status) {
        Pedido p = obterPedidoById(id);
        if (p != null) {
            p.alterarStatus(status);
            salvarDados();
            return true;
        }
        return false;
    }

    /**
     * Retorna a lista de todos os pedidos cadastrados.
     *
     * @return Lista completa de pedidos
     */
    public ArrayList<Pedido> obterTodosPedidos() {
        return new ArrayList<>(pedidos);
    }

    /**
     * Retorna o usuário que está atualmente logado no sistema.
     *
     * @return Usuário logado ou null se nenhum estiver autenticado
     */
    public Usuario obterUsuarioLogado() {
        return usuarioLogado;
    }

    /**
     * Verifica se há algum usuário logado no momento.
     *
     * @return true se há usuário logado
     */
    public boolean usuarioEstaLogado() {
        return usuarioLogado != null;
    }

    /**
     * Encerra a sessão do usuário logado, realizando logout.
     */
    public void logout() {
        usuarioLogado = null;
    }
}
