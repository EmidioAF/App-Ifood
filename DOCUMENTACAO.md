# Documentação Completa - Sistema iFood

## Índice

1. [Visão Geral](#visão-geral)
2. [Arquitetura do Sistema](#arquitetura-do-sistema)
3. [Componentes Principais](#componentes-principais)
4. [Camada de Modelo](#camada-de-modelo)
5. [Camada de Exceções](#camada-de-exceções)
6. [Camada de Persistência](#camada-de-persistência)
7. [Camada de Negócios](#camada-de-negócios)
8. [Camada de Interface](#camada-de-interface)
9. [Fluxos de Operação](#fluxos-de-operação)
10. [Boas Práticas](#boas-práticas)

---

## Visão Geral

**iFood Sistema** é uma aplicação Java completa para gerenciamento de Pedidos online, inspirada na plataforma iFood. O sistema implementa padrões de **Programação Orientada a Objetos (POO)**, **arquitetura em camadas** e **interface gráfica moderna em Swing**.

### Características Principais

- ✅ **Autenticação de Usuários**: Clientes e Restaurantes
- ✅ **Gerenciamento de Pedidos**: Criação, rastreamento e atualização
- ✅ **Cardápio Digital**: Produtos com preços e categorias
- ✅ **Sistema de Crédito**: Recarga e gestão de saldo
- ✅ **Persistência de Dados**: Serialização Java
- ✅ **Interface Gráfica**: 9 telas Swing completas

---

## Arquitetura do Sistema

```
┌─────────────────────────────────────────────┐
│         CAMADA DE APRESENTAÇÃO              │
│  (9 Telas Swing - Interface com Usuário)    │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│       CAMADA DE NEGÓCIOS (Controller)       │
│   Gerenciador.java + Sistema.java           │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│         CAMADA DE MODELO (Entidades)        │
│  Usuario, Cliente, Restaurante, Produto...  │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│      CAMADA DE PERSISTÊNCIA (Dados)         │
│  ArquivoUtils.java (Serialização)           │
└─────────────────────────────────────────────┘
                      ↓
┌─────────────────────────────────────────────┐
│         CAMADA DE EXCEÇÕES                  │
│  PedidoException e subclasses               │
└─────────────────────────────────────────────┘
```

---

## Componentes Principais

### Estrutura de Pacotes

```
src/main/
├── MODELO (Entidades)
│   ├── Usuario.java              (Classe abstrata base)
│   ├── Cliente.java              (Usuário do tipo Cliente)
│   ├── Restaurante.java          (Usuário do tipo Restaurante)
│   ├── Produto.java              (Itens do cardápio)
│   └── Pedido.java               (Pedido com Produtos)
│
├── EXCEÇÕES (Tratamento de Erros)
│   ├── PedidoException.java      (Exceção base)
│   ├── PedidoInvalidoException.java
│   ├── ArquivoNaoEncontradoException.java
│   └── UsuarioNaoAutenticadoException.java
│
├── PERSISTÊNCIA (Dados)
│   ├── ArquivoUtils.java         (Serialização e carregamento)
│   ├── DadosIniciais.java        (Dados de exemplo)
│   └── InitializerSistema.java   (Inicialização)
│
├── NEGÓCIOS (Controller)
│   ├── Gerenciador.java          (Lógica principal)
│   └── Sistema.java              (Ponto de entrada)
│
└── APRESENTAÇÃO (GUI)
    ├── TelaLogin.java
    ├── TelaCadastroCliente.java
    ├── TelaCadastroRestaurante.java
    ├── TelaClientePrincipal.java
    ├── TelaFazerPedido.java
    ├── TelaVerPedidosCliente.java
    ├── TelaRestaurantePrincipal.java
    ├── TelaGerenciarCardapio.java
    └── TelaVerPedidosRestaurante.java
```

---

## Camada de Modelo

### 1. **Usuario.java** - Classe Abstrata Base

**Propósito**: Define a estrutura comum para todos os tipos de usuários do sistema.

**Atributos**:
- `id`: String - Identificador único
- `nome`: String - Nome do usuário
- `email`: String - Email (usado para login)
- `senha`: String - Senha criptografada
- `telefone`: String - Contato
- `endereco`: String - Endereço de entrega/localização

**Métodos Abstratos**:
```java
public abstract void exibirMenu();        // Exibe menu específico
public abstract String getTipoUsuario();  // Retorna tipo (CLIENTE/RESTAURANTE)
```

**Métodos Concretos**:
```java
public boolean validarCredenciais(String email, String senha)  // Valida login
public String getId() / getNome() / getEmail() ... // Getters
public void setNome() / setEmail() ... // Setters
```

**Uso**: Serve como base para herança de `Cliente` e `Restaurante`.

---

### 2. **Cliente.java** - Entidade Cliente

**Herança**: `extends Usuario implements Serializable`

**Atributos**:
- `Pedidos`: ArrayList<Pedido> - Histórico de Pedidos do cliente
- `credito`: double - Saldo disponível para Pedidos
- `cpf`: String - CPF do cliente
- `ativo`: boolean - Status ativo/inativo

**Métodos Principais**:
```java
// Gestão de Crédito
public void adicionarCredito(double valor)         // Recarga saldo
public boolean deduzirCredito(double valor)        // Débito para pedido

// Gestão de Pedidos
public void adicionarPedido(Pedido pedido)         // Adiciona novo pedido
public ArrayList<Pedido> obterPedidos()            // Lista todos os Pedidos
public Pedido obterPedidoById(String idPedido)     // Busca por ID

// Getters
public double getCredito()
public String getCpf()
public boolean isAtivo()
public int getQuantidadePedidos()
```

**Validações**:
- Crédito não pode ser negativo
- CPF deve ser válido
- ID deve ser único

---

### 3. **Restaurante.java** - Entidade Restaurante

**Herança**: `extends Usuario implements Serializable`

**Atributos**:
- `cardapio`: ArrayList<Produto> - Produtos disponíveis
- `PedidosRecebidos`: ArrayList<Pedido> - Pedidos para este restaurante
- `cnpj`: String - Identificação legal
- `categoria`: String - Tipo de culinária (Pizzaria, Hambúrguer, etc)
- `taxaEntrega`: double - Taxa padrão de entrega
- `aberto`: boolean - Status de funcionamento

**Métodos Principais**:
```java
// Gestão de Cardápio
public void adicionarProduto(Produto produto)      // Adiciona item
public boolean removerProduto(String idProduto)    // Remove item
public ArrayList<Produto> obterCardapio()          // Lista cardápio
public Produto obterProdutoById(String idProduto)  // Busca produto

// Gestão de Pedidos
public void receberPedido(Pedido pedido)           // Recebe novo pedido
public ArrayList<Pedido> obterPedidosRecebidos()   // Pedidos recebidos

// Getters/Setters
public String getCnpj()
public double getTaxaEntrega()
public boolean isAberto()
public void setAberto(boolean aberto)
```

---

### 4. **Produto.java** - Entidade Produto

**Atributos**:
- `id`: String - Código único
- `nome`: String - Nome do produto
- `descricao`: String - Descrição detalhada
- `preco`: double - Valor em reais
- `categoria`: String - Classificação (Pizza, Bebida, etc)
- `idRestaurante`: String - Restaurante proprietário
- `ativo`: boolean - Disponível para compra

**Métodos**:
```java
public boolean validar()                   // Valida preço e nome
public String toMenuString()               // Formatação para exibição

// Exemplo de saída:
// [PROD001] Pizza Margherita - R$ 35.00 - Pizza
```

**Validações**:
- Preço deve ser maior que 0
- Nome não pode estar vazio
- ID obrigatório

---

### 5. **Pedido.java** - Entidade Pedido

**Atributos**:
```java
public enum StatusPedido {
    Pendentes("Pendentes"),
    Confirmado("Confirmado"),
    Preparando("Preparando"),
    Pronto("Pronto"),
    Entregue("Entregue"),
    Cancelado("Cancelado")
}
```

**Atributos Principais**:
- `id`: String - Identificador único
- `idCliente`: String - Referência ao cliente
- `idRestaurante`: String - Referência ao restaurante
- `Produtos`: ArrayList<Produto> - Itens do pedido
- `status`: StatusPedido - Estado atual
- `dataPedido`: LocalDateTime - Quando foi criado
- `total`: double - Valor total com taxa
- `taxaEntrega`: double - Taxa do restaurante

**Métodos**:
```java
public void adicionarProduto(Produto produto)      // Adiciona à compra
public boolean removerProduto(int indice)          // Remove item
public void alterarStatus(StatusPedido novoStatus) // Atualiza status
public boolean validar()                           // Valida pedido
public double obterSubtotal()                      // Sem taxa
public String obterDetalhes()                      // Descrição completa
```

---

## Camada de Exceções

### 1. **PedidoException.java** - Exceção Base

```java
public class PedidoException extends Exception {
    public PedidoException(String mensagem) { super(mensagem); }
    public PedidoException(String mensagem, Throwable causa) { 
        super(mensagem, causa); 
    }
}
```

**Uso**: Base para todas as exceções personalizadas do sistema.

---

### 2. **PedidoInvalidoException.java**

```java
public class PedidoInvalidoException extends PedidoException {
    public PedidoInvalidoException(String mensagem) { 
        super("Pedido Inválido: " + mensagem); 
    }
}
```

**Casos de Uso**:
- Pedido sem Produtos
- Crédito insuficiente
- Cliente ou restaurante inválido

---

### 3. **ArquivoNaoEncontradoException.java**

```java
public class ArquivoNaoEncontradoException extends PedidoException {
    public ArquivoNaoEncontradoException(String mensagem) { 
        super("Erro de Arquivo: " + mensagem); 
    }
}
```

**Casos de Uso**:
- Falha ao carregar dados
- Diretório não criado
- Arquivo corrompido

---

### 4. **UsuarioNaoAutenticadoException.java**

```java
public class UsuarioNaoAutenticadoException extends PedidoException {
    public UsuarioNaoAutenticadoException(String mensagem) { 
        super("Autenticação Falhou: " + mensagem); 
    }
}
```

**Casos de Uso**:
- Email/Senha incorretos
- Usuário não encontrado
- Sessão expirada

---

## Camada de Persistência

### **ArquivoUtils.java** - Serialização de Dados

**Propósito**: Gerenciar toda leitura/escrita de dados em arquivos `.dat` usando serialização Java.

**Constantes**:
```java
Caminho = "src/resources/"
Clientes = "src/resources/Clientes.dat"
Restaurantes = "src/resources/Restaurantes.dat"
Produtos = "src/resources/Produtos.dat"
Pedidos = "src/resources/Pedidos.dat"
```

**Métodos Públicos**:

```java
// Inicialização
public static void inicializarDiretorio()

// Clientes
public static void salvarClientes(ArrayList<Cliente> Clientes)
public static ArrayList<Cliente> carregarClientes()

// Restaurantes
public static void salvarRestaurantes(ArrayList<Restaurante> Restaurantes)
public static ArrayList<Restaurante> carregarRestaurantes()

// Produtos
public static void salvarProdutos(ArrayList<Produto> Produtos)
public static ArrayList<Produto> carregarProdutos()

// Pedidos
public static void salvarPedidos(ArrayList<Pedido> Pedidos)
public static ArrayList<Pedido> carregarPedidos()

// Utilitários
public static void limparTodosDados()       // Deleta todos os arquivos
public static boolean dadosExistem()        // Verifica se dados foram salvos
```

**Fluxo de Funcionamento**:

```
Salvar:    Objeto → ObjectOutputStream → Arquivo.dat
Carregar:  Arquivo.dat → ObjectInputStream → Objeto
```

**Tratamento de Erros**:
- Se arquivo não existe: retorna ArrayList vazio
- Se arquivo corrompido: lança ArquivoNaoEncontradoException
- Criação automática de diretórios

---

### **DadosIniciais.java** - Inicialização de Dados

**Propósito**: Popula o sistema com dados de exemplo na primeira execução.

**Dados Criados**:

```java
// Clientes
1. João Silva (joao@email.com) - Crédito: R$ 500,00
2. Maria Santos (maria@email.com) - Crédito: R$ 300,00

// Restaurantes
1. Pizza Delícia (Pizzaria) - Taxa: R$ 5,00
2. Burguer Master (Hambúrguer) - Taxa: R$ 3,50
3. Comida Árabe (Árabe) - Taxa: R$ 4,00

// Produtos (15 total)
- Pizza Margherita: R$ 35,00
- Pizza Pepperoni: R$ 38,00
- Hambúrguer Simples: R$ 18,00
- ... (mais 12 Produtos)
```

**Método Principal**:
```java
public static void inicializarDados(Gerenciador gerenciador)
```

---

### **InitializerSistema.java** - Inicializador do Sistema

**Propósito**: Controla a primeira execução do sistema.

**Funcionamento**:

1. Verifica se arquivo `.initialized` existe
2. Se NÃO existe:
   - Cria diretório `src/resources/`
   - Chama `DadosIniciais.inicializarDados()`
   - Cria marcador `.initialized`
3. Se existe: pula a inicialização

**Métodos**:
```java
public static void inicializar(Gerenciador gerenciador)     // Inicializa (se primeira vez)
public static void resetarSistema()                         // Remove dados e marcador
```

---

## Camada de Negócios

### **Gerenciador.java** - Controlador Principal

**Propósito**: Centraliza toda lógica de negócios do sistema.

**Responsabilidades**:
- Gerenciar Clientes (cadastro, autenticação)
- Gerenciar Restaurantes (cadastro, autenticação)
- Gerenciar Pedidos (criação, atualização)
- Carregar e salvar dados

**Atributos Principais**:
```java
private ArrayList<Cliente> Clientes
private ArrayList<Restaurante> Restaurantes
private ArrayList<Pedido> Pedidos
private Usuario usuarioLogado          // Usuário atual
```

**Métodos de Clientes**:
```java
public boolean cadastrarCliente(Cliente c)
// Cadastra novo cliente
// Validação: ID único
// Retorna: true se sucesso

public Cliente autenticarCliente(String email, String senha) throws UsuarioNaoAutenticadoException
// Autentica cliente com email/senha
// Exceção: Se credenciais inválidas

public Cliente obterClienteById(String id)
// Busca cliente por ID

public ArrayList<Cliente> obterClientesAtivos()
// Retorna apenas Clientes ativos

public ArrayList<Cliente> obterTodosClientes()
// Retorna todos os Clientes
```

**Métodos de Restaurantes**:
```java
public boolean cadastrarRestaurante(Restaurante r)
// Cadastra novo restaurante

public Restaurante autenticarRestaurante(String email, String senha) throws UsuarioNaoAutenticadoException
// Autentica restaurante

public Restaurante obterRestauranteById(String id)
// Busca restaurante por ID

public ArrayList<Restaurante> obterRestaurantesAbertos()
// Retorna Restaurantes abertos

public ArrayList<Restaurante> obterTodosRestaurantes()
// Retorna todos os Restaurantes
```

**Métodos de Pedidos**:
```java
public boolean criarPedido(Pedido pedido) throws PedidoInvalidoException
// Cria novo pedido
// Validações:
//   - Pedido válido (tem Produtos)
//   - Cliente existe e tem crédito
//   - Restaurante existe
//   - Deduz crédito do cliente

public Pedido obterPedidoById(String id)
// Busca pedido por ID

public boolean atualizarStatusPedido(String idPedido, Pedido.StatusPedido novoStatus)
// Atualiza estado do pedido

public ArrayList<Pedido> obterTodosPedidos()
// Retorna todos os Pedidos
```

**Métodos de Persistência**:
```java
public void carregarDados()
// Carrega dados dos arquivos
// Chamado no construtor

public void salvarDados()
// Salva dados em arquivos
// Chamado após mudanças
```

---

### **Sistema.java** - Ponto de Entrada

**Propósito**: Classe com método main que inicia toda a aplicação.

```java
public class Sistema {
    public static void main(String[] args) {
        // 1. Define Look & Feel do sistema
        UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        
        // 2. Executa no Event Dispatch Thread (thread de GUI)
        SwingUtilities.invokeLater(() -> {
            Gerenciador g = new Gerenciador();
            InitializerSistema.inicializar(g);
            new TelaLogin(g).setVisible(true);
        });
    }
}
```

**Fluxo**:
```
Sistema.main()
    ↓
Define Look & Feel
    ↓
Cria Gerenciador (carrega dados)
    ↓
InitializerSistema (se primeira vez)
    ↓
TelaLogin (abre tela de login)
```

---

## Camada de Interface (Swing)

### **TelaLogin.java** - Autenticação

**Componentes**:
- Campo de email (JTextField)
- Campo de senha (JPasswordField)
- ComboBox tipo usuário (Cliente/Restaurante)
- Botões: Entrar, Cadastro

**Fluxo**:
```
Usuário seleciona tipo
    ↓
Digita email e senha
    ↓
Clica "ENTRAR"
    ↓
Gerenciador.autenticar()
    ↓
Se sucesso → Abre tela do tipo
Se erro → Exibe mensagem
```

---

### **TelaCadastroCliente.java** - Registro de Cliente

**Campos**:
- ID, Nome, Email, Senha
- Telefone, Endereço, CPF

**Validações**:
- Campos obrigatórios preenchidos
- Email em formato válido
- CPF válido
- ID único

---

### **TelaCadastroRestaurante.java** - Registro de Restaurante

**Campos**:
- ID, Nome, Email, Senha
- Telefone, Endereço, CNPJ
- Categoria, Taxa de Entrega

**Validações**:
- CNPJ válido
- Categoria selecionada
- Taxa de entrega positiva

---

### **TelaClientePrincipal.java** - Menu Cliente

**Botões**:
1. **FAZER NOVO PEDIDO** → TelaFazerPedido
2. **VER MEUS Pedidos** → TelaVerPedidosCliente
3. **ADICIONAR CRÉDITO** → Dialog de recarga
4. **SAIR** → Volta para TelaLogin

**Exibições**:
- Mensagem "Bem-vindo, [Nome]!"
- Saldo atual em reais

---

### **TelaFazerPedido.java** - Criar Pedido

**Componentes**:
1. ComboBox de Restaurantes
2. Lista de Produtos (atualiza ao selecionar restaurante)
3. Botão "Adicionar Produto" (ao carrinho)
4. Label com Total (atualiza dinamicamente)
5. Botão "Confirmar Pedido"

**Fluxo**:
```
1. Seleciona restaurante
2. Lista atualiza com Produtos desse restaurante
3. Seleciona produto → Clica "Adicionar"
4. Produto vai para carrinho
5. Total recalcula (Σ preços + taxa)
6. Clica "Confirmar Pedido"
7. Sistema.criarPedido() cria o pedido
8. Crédito é deduzido
9. Volta para menu principal
```

---

### **TelaVerPedidosCliente.java** - Histórico de Pedidos

**Componentes**:
- Lista de Pedidos do cliente
- Área de detalhes (clique em pedido → exibe info)

**Informações Exibidas**:
- ID do pedido
- Status atual
- Data/Hora
- Produtos
- Total

---

### **TelaRestaurantePrincipal.java** - Menu Restaurante

**Botões**:
1. **GERENCIAR CARDÁPIO** → TelaGerenciarCardapio
2. **VER Pedidos** → TelaVerPedidosRestaurante
3. **ABRIR/FECHAR** → Alterna status
4. **SAIR** → Volta para TelaLogin

**Exibições**:
- Nome do restaurante
- Status (ABERTO/FECHADO)

---

### **TelaGerenciarCardapio.java** - Editar Cardápio

**Funcionalidades**:
1. Lista de Produtos atuais
2. Botão "ADICIONAR PRODUTO" → Dialog
3. Botão "REMOVER PRODUTO" → Deleta selecionado

**Dialog de Novo Produto**:
- ID, Nome, Descrição
- Preço, Categoria

---

### **TelaVerPedidosRestaurante.java** - Pedidos Recebidos

**Componentes**:
- Lista de Pedidos recebidos
- Detalhes do pedido (clique)
- ComboBox com status
- Botão "Atualizar Status"

**Funcionalidade**:
```
1. Restaurante vê lista de Pedidos
2. Clica em pedido → exibe detalhes
3. Seleciona novo status no ComboBox
4. Clica "Atualizar Status"
5. Status é alterado no sistema
```

---

## Fluxos de Operação

### Fluxo de Login e Autenticação

```
TelaLogin
    ↓ (usuário digita email, senha, seleciona tipo)
    ↓
Gerenciador.autenticar[Cliente/Restaurante]()
    ↓ (busca usuário no ArrayList)
    ↓ (valida credenciais)
    ↓
Se sucesso:
    ├→ Abre TelaClientePrincipal (se cliente)
    └→ Abre TelaRestaurantePrincipal (se restaurante)

Se erro:
    └→ Exibe mensagem na TelaLogin
```

---

### Fluxo de Fazer Pedido

```
Cliente clica "FAZER NOVO PEDIDO"
    ↓
TelaFazerPedido abre
    ↓
Cliente seleciona Restaurante
    ├→ Lista de Produtos atualiza
    ↓
Cliente seleciona Produtos
    ├→ Clica "Adicionar Produto"
    ├→ Produto vai para ArrayList<Produto>
    ├→ Total recalcula (soma + taxa)
    ↓
Cliente clica "Confirmar Pedido"
    ↓
Gerenciador.criarPedido(pedido)
    ├→ Valida pedido
    ├→ Valida crédito do cliente
    ├→ Deduz crédito
    ├→ Cria novo Pedido no ArrayList
    ├→ Adiciona Pedido ao Restaurante
    ├→ salvarDados()
    ↓
Mensagem "Pedido criado com sucesso!"
    ↓
Volta para TelaClientePrincipal
```

---

### Fluxo de Atualizar Status de Pedido

```
Restaurante clica "VER Pedidos"
    ↓
TelaVerPedidosRestaurante abre
    ├→ Carrega ArrayList de Pedidos do Restaurante
    ↓
Restaurante clica em pedido
    ├→ Exibe detalhes (Produtos, cliente, etc)
    ↓
Restaurante seleciona novo status no ComboBox
    ├→ Exemplo: Preparando → Pronto
    ↓
Restaurante clica "Atualizar Status"
    ↓
Gerenciador.atualizarStatusPedido(id, novoStatus)
    ├→ Busca pedido por ID
    ├→ Altera status
    ├→ salvarDados()
    ↓
Mensagem "Status atualizado!"
    ↓
Lista de Pedidos se atualiza
```

---

### Fluxo de Inicialização

```
Sistema.main() executa
    ↓
Define Look & Feel
    ↓
Cria Gerenciador
    ├→ Gerenciador.carregarDados()
    │   ├→ ArquivoUtils.inicializarDiretorio()
    │   ├→ ArquivoUtils.carregarClientes()
    │   ├→ ArquivoUtils.carregarRestaurantes()
    │   └→ ArquivoUtils.carregarPedidos()
    ↓
InitializerSistema.inicializar(g)
    ├→ Se arquivo .initialized NÃO existe:
    │   ├→ ArquivoUtils.inicializarDiretorio()
    │   ├→ DadosIniciais.inicializarDados(g)
    │   └→ Cria arquivo .initialized
    ├→ Se arquivo existe:
    │   └→ Pula inicialização
    ↓
TelaLogin fica visível
    ↓
Sistema aguarda ação do usuário
```

---

## Boas Práticas Implementadas

### 1. **Padrão MVC (Model-View-Controller)**
- **Model**: Entidades (Usuario, Cliente, Pedido, etc)
- **View**: Telas Swing (TelaLogin, TelaPrincipal, etc)
- **Controller**: Gerenciador.java

### 2. **Encapsulamento**
```java
private String email;           // Privado
public String getEmail() {      // Acesso controlado
    return email;
}
public void setEmail(String e) {
    this.email = e;
}
```

### 3. **Herança e Polimorfismo**
```java
public abstract class Usuario {
    public abstract void exibirMenu();  // Cada tipo implementa sua forma
}

class Cliente extends Usuario {
    @Override
    public void exibirMenu() { /* menu cliente */ }
}
```

### 4. **Tratamento de Exceções**
```java
try {
    Cliente c = gerenciador.autenticarCliente(email, senha);
} catch (UsuarioNaoAutenticadoException e) {
    JOptionPane.showMessageDialog(null, e.getMessage());
}
```

### 5. **Collections Genéricas**
```java
ArrayList<Cliente> Clientes;        // Type-safe
ArrayList<Pedido> Pedidos;          // Sem casting necessário
```

### 6. **Separação de Responsabilidades**
- Gerenciador: Lógica de negócios
- ArquivoUtils: Persistência
- Telas: Apenas interface

### 7. **Serialização para Persistência**
```java
ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(arquivo));
oos.writeObject(arrayList);  // Salva complexo objeto
```

### 8. **Validações em Múltiplos Níveis**
```java
// Nível 1: Tela (UI)
if (campoEmail.getText().isEmpty()) { /* erro */ }

// Nível 2: Gerenciador (lógica)
if (!existeClienteComId(id)) { /* erro */ }

// Nível 3: Exceção (fluxo de erro)
throw new UsuarioNaoAutenticadoException("...");
```

---

## Convenções de Código

### Nomenclatura
```java
// Classes: PascalCase
class TelaClientePrincipal { }
class Usuario { }

// Métodos: camelCase
public void adicionarProduto() { }
public ArrayList<Pedido> obterPedidos() { }

// Constantes: UPPER_SNAKE_CASE
private static final String CAMINHO = "src/resources/";

// Variáveis: camelCase
private ArrayList<Cliente> Clientes;
private double credito;
```

### Organização de Métodos
```java
public class Exemplo {
    // 1. Atributos privados
    private String propriedade;
    
    // 2. Construtor
    public Exemplo() { }
    
    // 3. Métodos de negócio
    public void operacao1() { }
    
    // 4. Getters e Setters
    public String getPropriedade() { }
    
    // 5. toString e equals
    @Override
    public String toString() { }
}
```

---

## Segurança

### Proteção de Dados
1. **ArrayList private**: Não pode ser acessado diretamente
2. **Retorno de cópias**: `return new ArrayList<>(lista);`
3. **Validação de entrada**: Todos os dados verificados
4. **Serialização segura**: Usa `serialVersionUID`

### Autenticação
1. Email + Senha obrigatórios
2. Credenciais validadas antes de login
3. Exceção lançada se inválidas

---

## Estatísticas do Projeto

| Métrica | Valor |
|---------|-------|
| Total de Classes | 18 |
| Linhas de Código | ~2,200 |
| Classes de Modelo | 5 |
| Exceções Personalizadas | 4 |
| Telas Swing | 9 |
| Enumerações | 1 (StatusPedido) |

---

## Suporte e Manutenção

**Estrutura de Pastas**:
```
src/resources/       → Dados persistidos (.dat)
src/main/*.java      → Código-fonte
```

**Limpeza de Dados**:
```java
// Para resetar sistema:
InitializerSistema.resetarSistema();
```

**Troubleshooting**:
- Arquivo corrompido? Deletar `src/resources/`
- Erro ao compilar? Verificar encoding UTF-8
- GUI lenta? Verificar heap memory

---

**Documento gerado em**: 13 de Novembro de 2025
**Versão**: 1.0
**Status**: ✅ Completo e Otimizado

