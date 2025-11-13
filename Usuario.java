package main;

import java.io.Serializable;

/**
 * Classe abstrata que representa um usuário genérico do sistema.
 * <p>
 * Pode ser especializada em Cliente ou Restaurante. 
 * Contém informações básicas como id, nome, email, senha, telefone e endereço.
 * </p>
 * Implementa Serializable para permitir persistência em arquivos.
 */
public abstract class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    
    /** Identificador único do usuário */
    protected String id;
    
    /** Nome do usuário */
    protected String nome;
    
    /** Email utilizado para login */
    protected String email;
    
    /** Senha do usuário */
    protected String senha;
    
    /** Telefone de contato */
    protected String telefone;
    
    /** Endereço do usuário */
    protected String endereco;
    
    /**
     * Construtor para criar um usuário genérico.
     * 
     * @param id Identificador único
     * @param nome Nome do usuário
     * @param email Email do usuário
     * @param senha Senha do usuário
     * @param telefone Telefone de contato
     * @param endereco Endereço do usuário
     */
    public Usuario(String id, String nome, String email, String senha, String telefone, String endereco) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.telefone = telefone;
        this.endereco = endereco;
    }
    
    /**
     * Método abstrato que deve ser implementado para exibir o menu
     * específico de cada tipo de usuário (Cliente ou Restaurante).
     */
    public abstract void exibirMenu();
    
    /**
     * Retorna o tipo de usuário (ex: "Cliente" ou "Restaurante").
     * 
     * @return String representando o tipo do usuário
     */
    public abstract String getTipoUsuario();
    
    // ==================== GETTERS ====================
    
    public String getId() {
        return id;
    }
    
    public String getNome() {
        return nome;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getSenha() {
        return senha;
    }
    
    public String getTelefone() {
        return telefone;
    }
    
    public String getEndereco() {
        return endereco;
    }
    
    // ==================== SETTERS ====================
    
    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public void setSenha(String senha) {
        this.senha = senha;
    }
    
    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
    
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    
    /**
     * Valida as credenciais fornecidas para autenticação.
     * 
     * @param email Email fornecido
     * @param senha Senha fornecida
     * @return true se email e senha conferem com os do usuário, false caso contrário
     */
    public boolean validarCredenciais(String email, String senha) {
        return this.email.equals(email) && this.senha.equals(senha);
    }
    
    /**
     * Representação em String do usuário.
     * Inclui id, nome, email, telefone, endereço e tipo do usuário.
     * 
     * @return String formatada representando o usuário
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", telefone='" + telefone + '\'' +
                ", endereco='" + endereco + '\'' +
                ", tipo=" + getTipoUsuario() +
                '}';
    }
}
