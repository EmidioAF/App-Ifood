package main;

import java.util.ArrayList;

/**
 * Classe responsável por popular o sistema com dados iniciais de exemplo.
 * 
 * <p>Essa classe cria alguns objetos pré-definidos de {@link Cliente}, {@link Restaurante}
 * e {@link Produto} para simular um ambiente funcional no sistema de delivery.
 * Os dados incluem clientes com crédito, restaurantes cadastrados e produtos variados.</p>
 * 
 * <p>É utilizada geralmente na inicialização do sistema para testes, demonstrações
 * ou durante a primeira execução do programa.</p>
 */
public class DadosIniciais {

    /**
     * Inicializa o sistema com um conjunto de dados padrão.
     *
     * <p>O método cria:
     * <ul>
     *   <li>2 clientes com créditos iniciais;</li>
     *   <li>3 restaurantes cadastrados;</li>
     *   <li>15 produtos distribuídos entre os restaurantes.</li>
     * </ul>
     * Após a criação, todos os dados são salvos pelo {@link Gerenciador}.</p>
     *
     * @param gerenciador instância do {@link Gerenciador} responsável por manipular e salvar os dados.
     */
    public static void inicializarDados(Gerenciador gerenciador) {
        try {
            // === Criação de Clientes ===
            Cliente c1 = new Cliente("CLI001", "João Silva", "joao@email.com", "senha123",
                    "(11) 99999-0001", "Rua A, 123", "123.456.789-00");
            c1.adicionarCredito(500.0);

            Cliente c2 = new Cliente("CLI002", "Maria Santos", "maria@email.com", "senha456",
                    "(11) 99999-0002", "Rua B, 456", "987.654.321-00");
            c2.adicionarCredito(300.0);

            gerenciador.cadastrarCliente(c1);
            gerenciador.cadastrarCliente(c2);

            // === Criação de Restaurantes ===
            Restaurante r1 = new Restaurante("REST001", "Pizza Delícia", "pizza@email.com",
                    "senha123", "(11) 3333-0001", "Av. Principal, 100",
                    "12.345.678/0001-00", "Pizzaria", 5.0);

            Restaurante r2 = new Restaurante("REST002", "Burguer Master", "burger@email.com",
                    "senha456", "(11) 3333-0002", "Av. Comercial, 200",
                    "87.654.321/0001-00", "Hambúrguer", 3.5);

            Restaurante r3 = new Restaurante("REST003", "Comida Árabe", "arabe@email.com",
                    "senha789", "(11) 3333-0003", "Rua Oriente, 300",
                    "11.111.111/0001-00", "Árabe", 4.0);

            gerenciador.cadastrarRestaurante(r1);
            gerenciador.cadastrarRestaurante(r2);
            gerenciador.cadastrarRestaurante(r3);

            // === Produtos - Pizzaria ===
            r1.adicionarProduto(new Produto("PROD001", "Pizza Margherita", "Tomate, mozzarella e manjericão", 35.0, "Pizza", "REST001"));
            r1.adicionarProduto(new Produto("PROD002", "Pizza Pepperoni", "Calabresa e queijo", 38.0, "Pizza", "REST001"));
            r1.adicionarProduto(new Produto("PROD003", "Pizza Vegetariana", "Vegetais variados", 32.0, "Pizza", "REST001"));
            r1.adicionarProduto(new Produto("PROD004", "Refrigerante 2L", "Coca-Cola ou Guaraná", 8.0, "Bebida", "REST001"));
            r1.adicionarProduto(new Produto("PROD005", "Salada Mista", "Alface, tomate e cenoura", 12.0, "Entrada", "REST001"));

            // === Produtos - Hamburgueria ===
            r2.adicionarProduto(new Produto("PROD006", "Hambúrguer Simples", "Pão, carne e alface", 18.0, "Hambúrguer", "REST002"));
            r2.adicionarProduto(new Produto("PROD007", "Hambúrguer Duplo", "Dupla carne com queijo", 28.0, "Hambúrguer", "REST002"));
            r2.adicionarProduto(new Produto("PROD008", "Batata Frita Grande", "Batata frita crocante", 10.0, "Acompanhamento", "REST002"));
            r2.adicionarProduto(new Produto("PROD009", "Milkshake", "Chocolate, morango ou baunilha", 12.0, "Bebida", "REST002"));
            r2.adicionarProduto(new Produto("PROD010", "Sorvete", "Escolha o sabor", 8.0, "Sobremesa", "REST002"));

            // === Produtos - Restaurante Árabe ===
            r3.adicionarProduto(new Produto("PROD011", "Espetim", "Carne com pão sírio", 22.0, "Prato Principal", "REST003"));
            r3.adicionarProduto(new Produto("PROD012", "Kibbeh", "Bola de trigo com carne", 14.0, "Entrada", "REST003"));
            r3.adicionarProduto(new Produto("PROD013", "Hummus", "Pasta de grão de bico", 10.0, "Entrada", "REST003"));
            r3.adicionarProduto(new Produto("PROD014", "Chá Árabe", "Bebida tradicional", 6.0, "Bebida", "REST003"));
            r3.adicionarProduto(new Produto("PROD015", "Baklava", "Doce árabe com mel", 11.0, "Sobremesa", "REST003"));

            // === Salvamento dos Dados ===
            gerenciador.salvarDados();
            System.out.println("✓ Dados iniciais carregados: 2 clientes, 3 restaurantes, 15 produtos");

        } catch (Exception e) {
            System.err.println("Erro ao inicializar: " + e.getMessage());
        }
    }
}
