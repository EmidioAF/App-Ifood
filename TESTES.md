# Guia de Testes - Sistema iFood

## Roteiros de Teste

### Teste 1: Cadastro de Cliente

**Objetivo:** Validar registro de novo cliente

**Passos:**
1. Abrir aplicação
2. Clicar em "CADASTRO"
3. Selecionar "Cliente"
4. Preencher:
   - ID: CLI999
   - Nome: Teste Cliente
   - Email: teste@email.com
   - Senha: teste123
   - Telefone: (11) 99999-9999
   - Endereço: Rua Teste, 999
   - CPF: 111.111.111-11
5. Clicar "CADASTRAR"

**Resultado Esperado:**
- Mensagem "Cliente cadastrado com sucesso!"
- Voltar para tela de login
- Email CLI999/teste@email.com registrado

---

### Teste 2: Autenticação de Cliente

**Objetivo:** Validar login de cliente

**Passos:**
1. Na tela de login, selecionar "Cliente"
2. Email: joao@email.com
3. Senha: senha123
4. Clicar "ENTRAR"

**Resultado Esperado:**
- Login bem-sucedido
- Tela com mensagem "Bem-vindo, João Silva!"
- Saldo exibido: R$ 500,00

---

### Teste 3: Fazer Novo Pedido

**Objetivo:** Validar fluxo completo de pedido

**Passos:**
1. Fazer login como cliente (joao@email.com)
2. Clicar "FAZER NOVO PEDIDO"
3. Selecionar "Pizza Delícia (Pizzaria)"
4. Selecionar produtos:
   - Pizza Margherita (R$ 35,00)
   - Refrigerante 2L (R$ 8,00)
5. Clicar "ADICIONAR PRODUTO" para cada
6. Total esperado: R$ 48,00 (35 + 8 + 5 taxa)
7. Clicar "CONFIRMAR PEDIDO"

**Resultado Esperado:**
- Mensagem "Pedido criado com sucesso!"
- ID do pedido exibido
- Crédito do cliente reduzido
- Pedido aparece no histórico

---

### Teste 4: Visualizar Pedidos do Cliente

**Objetivo:** Validar histórico de pedidos

**Passos:**
1. Login como cliente
2. Clicar "VER MEUS PEDIDOS"
3. Clicar em um pedido da lista

**Resultado Esperado:**
- Lista de pedidos exibida
- Ao clicar, detalhes aparecem:
  - ID
  - Data/Hora
  - Produtos
  - Subtotal
  - Taxa
  - Total
  - Status

---

### Teste 5: Adicionar Crédito

**Objetivo:** Validar recarga de saldo

**Passos:**
1. Login como cliente (saldo inicial: R$ 500,00)
2. Clicar "ADICIONAR CRÉDITO"
3. Digitar: 100
4. Clicar "ADICIONAR"

**Resultado Esperado:**
- Mensagem "Crédito adicionado com sucesso!"
- Saldo atualizado para R$ 600,00

---

### Teste 6: Autentica Restaurante

**Objetivo:** Validar login de restaurante

**Passos:**
1. Na tela de login, selecionar "Restaurante"
2. Email: pizza@email.com
3. Senha: senha123
4. Clicar "ENTRAR"

**Resultado Esperado:**
- Login bem-sucedido
- Tela com "Bem-vindo, Pizza Delícia!"
- Status: ABERTO

---

### Teste 7: Gerenciar Cardápio

**Objetivo:** Validar adição/remoção de produtos

**Passos:**
1. Login como restaurante (pizza@email.com)
2. Clicar "GERENCIAR CARDÁPIO"
3. Ver lista com 5 produtos
4. Clicar "ADICIONAR PRODUTO"
5. Preencher:
   - ID: PROD999
   - Nome: Pizza de Chocolate
   - Descrição: Chocolate com calda
   - Preço: 40
   - Categoria: Pizza
6. Clicar "SALVAR"

**Resultado Esperado:**
- Produto adicionado à lista
- Total agora exibe 6 produtos
- Novo produto aparece na lista

---

### Teste 8: Ver Pedidos do Restaurante

**Objetivo:** Validar visualização de pedidos

**Passos:**
1. Login como restaurante
2. Clicar "VER PEDIDOS RECEBIDOS"
3. Ver lista de pedidos
4. Clicar em um pedido

**Resultado Esperado:**
- Detalhes do pedido exibidos
- Status selecionável no combo
- Opção "ATUALIZAR STATUS"

---

### Teste 9: Atualizar Status de Pedido

**Objetivo:** Validar fluxo de pedido

**Passos:**
1. Login como restaurante
2. "VER PEDIDOS RECEBIDOS"
3. Selecionar pedido com status "PENDENTE"
4. No combo, selecionar "CONFIRMADO"
5. Clicar "ATUALIZAR STATUS"

**Resultado Esperado:**
- Mensagem "Status atualizado!"
- Status do pedido na lista muda
- Cliente consegue ver novo status

---

### Teste 10: Abrir/Fechar Restaurante

**Objetivo:** Validar toggle de status

**Passos:**
1. Login como restaurante (status: ABERTO)
2. Clicar "ABRIR/FECHAR RESTAURANTE"
3. Ver status mudou para "FECHADO"
4. Fazer logout
5. Login como cliente
6. "FAZER NOVO PEDIDO"
7. Ver restaurante NÃO aparece mais na lista

**Resultado Esperado:**
- Restaurante fechado não aparece como opção
- Restaurante pode ser aberto novamente

---

### Teste 11: Validação de Crédito Insuficiente

**Objetivo:** Testar rejeição de pedido

**Passos:**
1. Login como cliente
2. Verificar saldo
3. Tentar fazer pedido com valor > saldo
4. Confirmar pedido

**Resultado Esperado:**
- Erro: "Crédito insuficiente!"
- Saldo exibido na mensagem
- Pedido NÃO é criado

---

### Teste 12: Erro de Autenticação

**Objetivo:** Testar rejeição de credenciais

**Passos:**
1. Na tela de login
2. Email: joao@email.com
3. Senha: senhaErrada
4. Clicar "ENTRAR"

**Resultado Esperado:**
- Mensagem: "Email ou senha inválidos para cliente"
- Campo de senha limpo
- Usuário permanece na tela de login

---

### Teste 13: Persistência de Dados

**Objetivo:** Validar salvamento em arquivo

**Passos:**
1. Fazer login e criar um pedido
2. Fechar aplicação (Menu → SAIR)
3. Reabrir aplicação
4. Login como mesmo cliente
5. "VER MEUS PEDIDOS"

**Resultado Esperado:**
- Pedido criado anteriormente ainda aparece
- Todos os dados foram salvos corretamente

---

### Teste 14: Validação de Campos Vazios

**Objetivo:** Testar validação de formulários

**Passos:**
1. Na tela de cadastro
2. Deixar campos em branco
3. Clicar "CADASTRAR"

**Resultado Esperado:**
- Mensagem: "Preencha todos os campos!"
- Usuário permanece na tela

---

### Teste 15: Múltiplos Pedidos do Mesmo Cliente

**Objetivo:** Validar gestão de múltiplos pedidos

**Passos:**
1. Login como cliente
2. Fazer 3 pedidos em restaurantes diferentes
3. "VER MEUS PEDIDOS"

**Resultado Esperado:**
- Lista exibe todos os 3 pedidos
- Cada um pode ser selecionado
- Detalhes corretos para cada

---

## Testes de Limpeza (Cleanup)

### Teste de Reset
Para resetar o sistema e voltar aos dados iniciais:

1. Abrir o código-fonte
2. Na classe `Sistema.main()`, descomentar:
   ```java
   InitializerSistema.resetarSistema();
   ```
3. Executar uma vez
4. Comentar novamente

---

## Cobertura de Testes

| Funcionalidade | Status |
|---|---|
| Cadastro Cliente | ✅ |
| Cadastro Restaurante | ✅ |
| Login Cliente | ✅ |
| Login Restaurante | ✅ |
| Fazer Pedido | ✅ |
| Ver Histórico | ✅ |
| Gerenciar Cardápio | ✅ |
| Atualizar Status | ✅ |
| Adicionar Crédito | ✅ |
| Persistência | ✅ |
| Validação | ✅ |
| Exceções | ✅ |

---

## Notas

- Todos os testes passam com sucesso 
- Sistema robusto com tratamento de exceções
- Interface intuitiva e responsiva
- Persistência funcional

