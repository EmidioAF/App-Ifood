package main;

import java.io.*;
import java.util.ArrayList;
import java.nio.file.*;

/**
 * Classe utilitária responsável por gerenciar as operações de leitura e gravação
 * de arquivos binários (.dat) usados pelo sistema de delivery.
 * 
 * <p>Essa classe lida com clientes, restaurantes, produtos e pedidos,
 * garantindo que os dados sejam persistidos e recuperados corretamente
 * do diretório definido.</p>
 */
public class ArquivoUtils {

    /** Caminho base onde os arquivos de dados serão armazenados. */
    private static final String Caminho = "src/resources/";

    /** Caminho do arquivo que armazena os clientes. */
    private static final String Clientes = Caminho + "Clientes.dat";

    /** Caminho do arquivo que armazena os restaurantes. */
    private static final String Restaurantes = Caminho + "Restaurantes.dat";

    /** Caminho do arquivo que armazena os produtos. */
    private static final String Produtos = Caminho + "Produtos.dat";

    /** Caminho do arquivo que armazena os pedidos. */
    private static final String Pedidos = Caminho + "Pedidos.dat";

    /**
     * Garante que o diretório de armazenamento exista, criando-o se necessário.
     *
     * @throws ArquivoNaoEncontradoException se ocorrer erro ao criar o diretório.
     */
    public static void inicializarDiretorio() throws ArquivoNaoEncontradoException {
        try {
            Path dir = Paths.get(Caminho);
            if (!Files.exists(dir)) Files.createDirectories(dir);
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Erro ao criar diretório: " + e.getMessage());
        }
    }

    /**
     * Salva a lista de clientes em arquivo.
     *
     * @param Clientes lista de clientes a ser salva.
     * @throws ArquivoNaoEncontradoException se ocorrer erro ao salvar o arquivo.
     */
    public static void salvarClientes(ArrayList<Cliente> Clientes) throws ArquivoNaoEncontradoException {
        salvarObjeto(Clientes, ArquivoUtils.Clientes, "Clientes");
    }

    /**
     * Carrega a lista de clientes do arquivo.
     *
     * @return lista de clientes carregada, ou vazia se o arquivo não existir.
     * @throws ArquivoNaoEncontradoException se ocorrer erro na leitura do arquivo.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Cliente> carregarClientes() throws ArquivoNaoEncontradoException {
        return carregarObjeto(ArquivoUtils.Clientes, "Clientes");
    }

    /**
     * Salva a lista de restaurantes em arquivo.
     *
     * @param Restaurantes lista de restaurantes a ser salva.
     * @throws ArquivoNaoEncontradoException se ocorrer erro ao salvar o arquivo.
     */
    public static void salvarRestaurantes(ArrayList<Restaurante> Restaurantes) throws ArquivoNaoEncontradoException {
        salvarObjeto(Restaurantes, ArquivoUtils.Restaurantes, "Restaurantes");
    }

    /**
     * Carrega a lista de restaurantes do arquivo.
     *
     * @return lista de restaurantes carregada, ou vazia se o arquivo não existir.
     * @throws ArquivoNaoEncontradoException se ocorrer erro na leitura do arquivo.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Restaurante> carregarRestaurantes() throws ArquivoNaoEncontradoException {
        return carregarObjeto(ArquivoUtils.Restaurantes, "Restaurantes");
    }

    /**
     * Salva a lista de produtos em arquivo.
     *
     * @param Produtos lista de produtos a ser salva.
     * @throws ArquivoNaoEncontradoException se ocorrer erro ao salvar o arquivo.
     */
    public static void salvarProdutos(ArrayList<Produto> Produtos) throws ArquivoNaoEncontradoException {
        salvarObjeto(Produtos, ArquivoUtils.Produtos, "Produtos");
    }

    /**
     * Carrega a lista de produtos do arquivo.
     *
     * @return lista de produtos carregada, ou vazia se o arquivo não existir.
     * @throws ArquivoNaoEncontradoException se ocorrer erro na leitura do arquivo.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Produto> carregarProdutos() throws ArquivoNaoEncontradoException {
        return carregarObjeto(ArquivoUtils.Produtos, "Produtos");
    }

    /**
     * Salva a lista de pedidos em arquivo.
     *
     * @param Pedidos lista de pedidos a ser salva.
     * @throws ArquivoNaoEncontradoException se ocorrer erro ao salvar o arquivo.
     */
    public static void salvarPedidos(ArrayList<Pedido> Pedidos) throws ArquivoNaoEncontradoException {
        salvarObjeto(Pedidos, ArquivoUtils.Pedidos, "Pedidos");
    }

    /**
     * Carrega a lista de pedidos do arquivo.
     *
     * @return lista de pedidos carregada, ou vazia se o arquivo não existir.
     * @throws ArquivoNaoEncontradoException se ocorrer erro na leitura do arquivo.
     */
    @SuppressWarnings("unchecked")
    public static ArrayList<Pedido> carregarPedidos() throws ArquivoNaoEncontradoException {
        return carregarObjeto(ArquivoUtils.Pedidos, "Pedidos");
    }

    /**
     * Método genérico para salvar um objeto em arquivo.
     *
     * @param obj objeto a ser salvo.
     * @param arquivo caminho do arquivo onde será salvo.
     * @param tipo descrição textual do tipo de dado salvo (ex: "Clientes").
     * @throws ArquivoNaoEncontradoException se ocorrer erro ao salvar o arquivo.
     */
    private static void salvarObjeto(Object obj, String arquivo, String tipo) throws ArquivoNaoEncontradoException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo))) {
            oos.writeObject(obj);
        } catch (IOException e) {
            throw new ArquivoNaoEncontradoException("Erro ao salvar " + tipo + ": " + e.getMessage());
        }
    }

    /**
     * Método genérico para carregar um objeto de um arquivo.
     *
     * @param <T> tipo genérico da lista carregada.
     * @param arquivo caminho do arquivo.
     * @param tipo descrição textual do tipo de dado.
     * @return lista carregada do arquivo ou lista vazia se o arquivo não existir.
     * @throws ArquivoNaoEncontradoException se ocorrer erro ao ler o arquivo.
     */
    @SuppressWarnings("unchecked")
    private static <T> ArrayList<T> carregarObjeto(String arquivo, String tipo) throws ArquivoNaoEncontradoException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
            return (ArrayList<T>) ois.readObject();
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException | ClassNotFoundException e) {
            throw new ArquivoNaoEncontradoException("Erro ao carregar " + tipo + ": " + e.getMessage());
        }
    }

    /**
     * Exclui todos os arquivos de dados (Clientes, Restaurantes, Produtos, Pedidos).
     * Usado para limpar completamente o armazenamento local do sistema.
     */
    public static void limparTodosDados() {
        try {
            Files.deleteIfExists(Paths.get(Clientes));
            Files.deleteIfExists(Paths.get(Restaurantes));
            Files.deleteIfExists(Paths.get(Produtos));
            Files.deleteIfExists(Paths.get(Pedidos));
        } catch (IOException e) {
            System.err.println("Erro ao limpar: " + e.getMessage());
        }
    }

    /**
     * Verifica se todos os arquivos de dados (Clientes, Restaurantes, Produtos, Pedidos) existem.
     *
     * @return {@code true} se todos os arquivos existirem, {@code false} caso contrário.
     */
    public static boolean dadosExistem() {
        return Files.exists(Paths.get(Clientes)) &&
               Files.exists(Paths.get(Restaurantes)) &&
               Files.exists(Paths.get(Produtos)) &&
               Files.exists(Paths.get(Pedidos));
    }
}
