package main;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Classe responsável por inicializar o sistema na primeira execução.
 * 
 * <p>Essa classe verifica se o sistema já foi inicializado anteriormente 
 * através de um arquivo marcador. Caso seja a primeira execução, 
 * cria os diretórios necessários, carrega dados iniciais (clientes, 
 * restaurantes e produtos) e registra a inicialização.</p>
 * 
 * <p>Também oferece um método de reset que apaga todos os dados e 
 * reinicia o estado do sistema, voltado apenas para fins de teste.</p>
 */
public class InitializerSistema {

    /** Caminho do arquivo marcador que indica se o sistema já foi inicializado */
    private static final String ARQUIVO_MARCA = "src/resources/.initialized";

    /**
     * Inicializa o sistema, criando dados e diretórios iniciais caso seja a primeira execução.
     *
     * @param gerenciador Instância do {@link Gerenciador} responsável pelo controle do sistema
     */
    public static void inicializar(Gerenciador gerenciador) {
        try {
            // Verifica se já existe o marcador de inicialização
            if (!Files.exists(Paths.get(ARQUIVO_MARCA))) {
                System.out.println("\n╔════════════════════════════════════════╗");
                System.out.println("║ Primeira Execução - Inicializando...   ║");
                System.out.println("╚════════════════════════════════════════╝\n");

                // Cria o diretório base de armazenamento
                ArquivoUtils.inicializarDiretorio();

                // Carrega os dados iniciais (clientes, restaurantes e produtos)
                DadosIniciais.inicializarDados(gerenciador);

                // Cria o arquivo marcador para indicar que o sistema já foi inicializado
                Files.createFile(Paths.get(ARQUIVO_MARCA));

                System.out.println("\n✓ Sistema inicializado com sucesso!\n");
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar sistema: " + e.getMessage());
        }
    }

    /**
     * Reseta completamente o sistema, apagando todos os dados armazenados e
     * removendo o arquivo de inicialização.
     * 
     * <p><b>ATENÇÃO:</b> Este método deve ser usado apenas para fins de teste ou desenvolvimento,
     * pois apaga permanentemente todos os registros.</p>
     */
    public static void resetarSistema() {
        try {
            // Remove todos os dados armazenados
            ArquivoUtils.limparTodosDados();

            // Exclui o arquivo marcador de inicialização
            Files.deleteIfExists(Paths.get(ARQUIVO_MARCA));

            System.out.println("✓ Sistema resetado.");
        } catch (Exception e) {
            System.err.println("Erro ao resetar: " + e.getMessage());
        }
    }
}
