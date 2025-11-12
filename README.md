# App-Ifood
# Sistema de Pedidos

Projeto desenvolvido em Java, aplicando os conceitos de Programação Orientada a Objetos (POO).  
O sistema simula o funcionamento básico de uma plataforma de pedidos online, com funcionalidades para clientes e restaurantes.

---

# Funcionalidades
## Cliente
- Login de cliente.
- Realizar pedido.
- Visualizar pedidos realizados.
- Cálculo automático do valor total do pedido.

## Restaurante
- Login de restaurante.
- Adicionar, visualizar e remover produtos do cardápio.
- Gerenciar os pedidos recebidos.

## Sistema
- Leitura e escrita de dados em arquivos .txt.
- Tratamento de exceções personalizadas.
- Persistência de objetos (pedidos, produtos e usuários).
- Interface gráfica básica com JavaFX.

---

# Estrutura do Projeto
## Diagrama de Classes (conceitual)

Usuario (abstrata)
├── Cliente
│ └── listaPedidos : ArrayList<Pedido>
└── Restaurante
└── listaProdutos : ArrayList<Produto>

Produto
└── nome, preco, categoria

Pedido
└── cliente, listaProdutos, total

ArquivoUtils
└── lerCSV(), salvarCSV()

PedidoException
└── extends Exception

---

# Tecnologias Utilizadas
- Java
- JavaFX (interface gráfica)
- Collections Framework (ArrayList)
- Manipulação de Arquivos (java.io)
- Tratamento de Exceções
- Git e GitHub (controle de versão)

---

# Requisitos Técnicos Atendidos
| Requisito | Implementação |
| Encapsulamento | uso de getters/setters |
| 5+ classes | (Usuario, Cliente, Restaurante, Produto, Pedido, etc.) |
| Classe abstrata e método abstrato | (Usuario, exibirMenu()) |
| Herança e polimorfismo | Cliente/Restaurante herdam Usuario |
| Sobrescrita de método | exibirMenu() |
| Associação entre classes | Pedido → Cliente/Produto |
| Coleção de objetos | ArrayList |
| Classe derivada de Exception | PedidoException |
| Interface gráfica | JavaFX |
| Leitura e gravação de arquivos | ArquivoUtils |
| Persistência de objetos | salvar e recuperar pedidos/produtos |
| Tratamento de exceção | try/catch com PedidoException |

---

# Estrutura de Diretórios
├── main/
│ ├── Sistema.java
│ ├── Usuario.java
│ ├── Cliente.java
│ ├── Restaurante.java
│ ├── Produto.java
│ ├── Pedido.java
│ ├── ArquivoUtils.java
│ └── PedidoException.java
└── resources/
├── produtos.csv
└── pedidos.csv

---

# Fluxograma Lógico


---

# Fluxo Lógico (com base no fluxograma)

<img width="904" height="1002" alt="image" src="https://github.com/user-attachments/assets/58c1396d-b54e-46bd-8073-165dc3733e1b" />

