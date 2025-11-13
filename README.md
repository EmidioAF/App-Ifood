# Sistema iFood Grupo 10- Documentação Completa

## Visão Geral

Projeto desenvolvido em Java, aplicando os conceitos de Programação Orientada a Objetos (POO).
O sistema simula o funcionamento básico de uma plataforma de pedidos online, com funcionalidades para clientes e restaurantes.
---

## Arquitetura

### Estrutura de Diretórios

```
src/main/
├── Sistema.java                      # Classe principal do sistema
├── InitializerSistema.java          # Inicializador de dados
├── Gerenciador.java                 # Gerenciador central
│
├── Usuario.java                      # Classe abstrata base
├── Cliente.java                      # Subclasse de usuário (cliente)
├── Restaurante.java                 # Subclasse de usuário (restaurante)
├── Produto.java                      # Modelo de produto
├── Pedido.java                       # Modelo de pedido
│
├── PedidoException.java             # Exceção base personalizada
├── PedidoInvalidoException.java      # Exceção de pedido inválido
├── ArquivoNaoEncontradoException.java # Exceção de arquivo
├── UsuarioNaoAutenticadoException.java # Exceção de autenticação
│
├── ArquivoUtils.java                # Utilitário de persistência
├── DadosIniciais.java               # Dados de exemplo
│
├── TelaLogin.java                   # Tela de login
├── TelaCadastroCliente.java        # Cadastro de cliente
├── TelaCadastroRestaurante.java    # Cadastro de restaurante
├── TelaClientePrincipal.java       # Menu principal do cliente
├── TelaFazerPedido.java            # Tela de fazer pedido
├── TelaVerPedidosCliente.java      # Histórico de pedidos (cliente)
├── TelaRestaurantePrincipal.java   # Menu principal do restaurante
├── TelaGerenciarCardapio.java      # Gerenciamento de cardápio
└── TelaVerPedidosRestaurante.java  # Visualização de pedidos (restaurante)

resources/
└── (armazena arquivos de persistência)
```

---

## Conceitos de POO Implementados

### 1. **Encapsulamento**
- Todos os atributos são **private** com getters/setters
- Exemplo: `Cliente.java`, `Produto.java`

### 2. **Herança**
- `Usuario` é classe **abstrata** base
- `Cliente` e `Restaurante` herdam de `Usuario`
- Exemplo: `class Cliente extends Usuario`

### 3. **Polimorfismo**
- Método abstrato `exibirMenu()` sobrescrito em `Cliente` e `Restaurante`
- `TipoUsuario` retorna tipo específico de cada usuário

### 4. **Abstração**
- Classe abstrata `Usuario` define contrato para subclasses
- Métodos abstratos: `exibirMenu()`, `getTipoUsuario()`

### 5. **Interfaces e Serialização**
- `Serializable` implementado em todas as classes de dados
- Permite persistência de objetos em arquivos

---

## Atributos e Métodos

### Usuario (Classe Abstrata)
**Atributos:** 
- `id`, `nome`, `email`, `senha`, `telefone`, `endereco`

**Métodos:**
- `exibirMenu()` *(abstrato)*
- `getTipoUsuario()` *(abstrato)*
- `validarCredenciais(String, String)`
- Getters e Setters

### Cliente
**Atributos Adicionais:**
- `ArrayList<Pedido> pedidos`
- `double credito`
- `String cpf`
- `boolean ativo`

**Métodos:**
- `adicionarPedido(Pedido)`
- `obterPedidos()`
- `adicionarCredito(double)`
- `deduzirCredito(double)`
- `exibirMenu()` *(sobrescrito)*

### Restaurante
**Atributos Adicionais:**
- `ArrayList<Produto> cardapio`
- `ArrayList<Pedido> pedidosRecebidos`
- `String cnpj`
- `String categoria`
- `double taxaEntrega`
- `boolean aberto`

**Métodos:**
- `adicionarProduto(Produto)`
- `removerProduto(String)`
- `obterCardapio()`
- `receberPedido(Pedido)`
- `obterPedidosRecebidos()`
- `exibirMenu()` *(sobrescrito)*

### Produto
**Atributos:**
- `id`, `nome`, `descricao`, `preco`, `categoria`, `idRestaurante`, `ativo`

**Métodos:**
- `validar()`
- `toMenuString()`
- Getters e Setters

### Pedido
**Atributos:**
- `id`, `idCliente`, `idRestaurante`
- `ArrayList<Produto> produtos`
- `StatusPedido status` (enum)
- `LocalDateTime dataPedido`
- `double total`
- `String observacoes`
- `double taxaEntrega`

**Métodos:**
- `adicionarProduto(Produto)`
- `removerProduto(int)`
- `recalcularTotal()`
- `validar()`
- `obterSubtotal()`
- `alterarStatus(StatusPedido)`
- `obterDetalhes()`

---

## Persistência de Dados

### ArquivoUtils
Classe responsável por salvar/carregar dados usando **serialização**:

```java
// Salvar
ArquivoUtils.salvarClientes(clientes);
ArquivoUtils.salvarRestaurantes(restaurantes);
ArquivoUtils.salvarProdutos(produtos);
ArquivoUtils.salvarPedidos(pedidos);

// Carregar
ArrayList<Cliente> clientes = ArquivoUtils.carregarClientes();
ArrayList<Restaurante> restaurantes = ArquivoUtils.carregarRestaurantes();
ArrayList<Produto> produtos = ArquivoUtils.carregarProdutos();
ArrayList<Pedido> pedidos = ArquivoUtils.carregarPedidos();
```

**Localização dos arquivos:**
- `src/resources/clientes.dat`
- `src/resources/restaurantes.dat`
- `src/resources/produtos.dat`
- `src/resources/pedidos.dat`

---

## Tratamento de Exceções Personalizadas

### Hierarquia de Exceções

```
PedidoException (classe base)
├── PedidoInvalidoException
├── ArquivoNaoEncontradoException
└── UsuarioNaoAutenticadoException
```

### Uso

```java
try {
    gerenciador.criarPedido(pedido);
} catch (PedidoInvalidoException e) {
    System.err.println(e.getMessage());
} catch (ArquivoNaoEncontradoException e) {
    System.err.println("Erro ao salvar: " + e.getMessage());
} catch (UsuarioNaoAutenticadoException e) {
    System.err.println("Autenticação falhou: " + e.getMessage());
}
```

---

## Interface Gráfica (Swing)

### Telas Implementadas

1. **TelaLogin**
   - Autenticação de Cliente ou Restaurante
   - Acesso a cadastro

2. **TelaCadastroCliente** e **TelaCadastroRestaurante**
   - Registro de novos usuários
   - Validação de campos

3. **TelaClientePrincipal**
   - Menu com opções: Fazer Pedido, Ver Pedidos, Adicionar Crédito, Sair
   - Exibe saldo disponível

4. **TelaFazerPedido**
   - Seleção de restaurante
   - Seleção de produtos
   - Cálculo de total
   - Confirmação de pedido

5. **TelaVerPedidosCliente**
   - Histórico de pedidos
   - Detalhes de cada pedido
   - Status de entrega

6. **TelaRestaurantePrincipal**
   - Menu: Gerenciar Cardápio, Ver Pedidos, Abrir/Fechar, Sair
   - Status do restaurante

7. **TelaGerenciarCardapio**
   - Adicionar novos produtos
   - Remover produtos
   - Lista de produtos

8. **TelaVerPedidosRestaurante**
   - Visualizar pedidos recebidos
   - Atualizar status (PENDENTE → CONFIRMADO → PREPARANDO → PRONTO → ENTREGUE)
   - Possibilidade de cancelar

---

## Fluxo de Operações

### Fluxo do Cliente
1. Login ou Cadastro
2. Visualizar Restaurantes Abertos
3. Selecionar Restaurante
4. Adicionar Produtos ao Carrinho
5. Confirmar Pedido (deduz crédito)
6. Visualizar Status do Pedido

### Fluxo do Restaurante
1. Login ou Cadastro
2. Gerenciar Cardápio (Adicionar/Remover Produtos)
3. Visualizar Pedidos Recebidos
4. Atualizar Status dos Pedidos
5. Abrir/Fechar Restaurante

---

## Dados Iniciais

O sistema vem com dados de exemplo como:

### Clientes
- **João Silva** | joao@email.com | CPF: 123.456.789-00 | Saldo: R$ 500,00
- **Maria Santos** | maria@email.com | CPF: 987.654.321-00 | Saldo: R$ 300,00

### Restaurantes
- **Pizza Delícia** | Categoria: Pizzaria | Taxa: R$ 5,00
- **Burguer Master** | Categoria: Hambúrguer | Taxa: R$ 3,50
- **Comida Árabe** | Categoria: Árabe | Taxa: R$ 4,00

### Produtos
- 5 produtos por restaurante
- Preços variáveis
- Categorias: Pizza, Hambúrguer, Bebida, Entrada, Sobremesa, etc.

---

## Comentários e Documentação

Todas as classes e métodos possuem:
- **JavaDoc comments** explicativo
- **Descrição de parâmetros** com `@param`
- **Tipo de retorno** com `@return`

---

## Requisitos Atendidos

- ✓ POO: Encapsulamento, Herança, Polimorfismo, Abstração
- ✓ Classe Abstrata: `Usuario`
- ✓ Subclasses: `Cliente`, `Restaurante`
- ✓ Exceções Personalizadas: `PedidoException` e derivadas
- ✓ ArrayList para coleções
- ✓ Persistência: Serialização em arquivos TXT
- ✓ Swing: Interface completa com múltiplas telas
- ✓ Tratamento robusto de erros
- ✓ Documentação e comentários
- ✓ 10+ atributos e 10+ métodos por classe
- ✓ Cálculo de total com taxa de entrega
- ✓ Autenticação e autorização de usuários

---

## Segurança

- Senhas armazenadas (sem criptografia neste prototipo)
- Validação de campos de entrada
- Verificação de crédito antes de pedido
- Tratamento de exceções em operações críticas

---

## Fluxograma 

<img width="1131" height="1600" alt="image" src="https://github.com/user-attachments/assets/6d9a10cc-7a0a-4a95-a016-0de69d42aa25" />

## Fluxo Lógico

<img width="1131" height="1600" alt="image" src="https://github.com/user-attachments/assets/7d5b193c-60a7-4364-8ae1-b965b7cb6411" />



