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

<img width="473" height="268" alt="image" src="https://github.com/user-attachments/assets/64b9d74f-20d4-4e31-b93b-9a7d5e7955af" />

---

# Estrutura de Diretórios

<img width="187" height="248" alt="image" src="https://github.com/user-attachments/assets/ce7195b7-7057-4ce9-b914-3b641c9f3926" />

---

# Fluxograma Lógico


---

# Fluxo Lógico (com base no fluxograma)

<img width="904" height="1002" alt="image" src="https://github.com/user-attachments/assets/58c1396d-b54e-46bd-8073-165dc3733e1b" />

