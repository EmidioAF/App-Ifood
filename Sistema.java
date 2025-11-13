package main;

import javax.swing.*;

/**
 * Classe principal do sistema de delivery.
 * 
 * <p>É responsável por inicializar o ambiente gráfico (Java Swing), configurar o estilo visual
 * do sistema e iniciar a tela de login. Também instancia o {@link Gerenciador}, que controla 
 * as operações principais do sistema.</p>
 */
public class Sistema {

    /**
     * Método principal do sistema.  
     * <p>Define o Look and Feel do Java Swing para se adequar ao sistema operacional,
     * inicializa o gerenciador de dados e exibe a tela de login.</p>
     *
     * @param args argumentos de linha de comando (não utilizados).
     */
    public static void main(String[] args) {
        try {
            // Define o visual padrão do sistema operacional
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Erro ao definir Look and Feel");
        }

        // Inicia a interface gráfica na thread correta do Swing
        SwingUtilities.invokeLater(() -> {
            Gerenciador g = new Gerenciador();
            InitializerSistema.inicializar(g);
            new TelaLogin(g).setVisible(true);
        });
    }
}
