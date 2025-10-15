# 🍕 iPitsa - Migração para PostgreSQL (Neon)

## ✅ Mudanças Implementadas

### 1. **Dependências Atualizadas** (`pom.xml`)
- ✅ Adicionado `org.postgresql:postgresql` driver
- ✅ Mantido H2 para testes locais (opcional)

### 2. **Configuração Atualizada** (`application.yaml`)
```yaml
spring:
  datasource:
    url: jdbc:postgresql://ep-rapid-sea-ad4pirrg-pooler.c-2.us-east-1.aws.neon.tech/neondb?sslmode=require&channel_binding=require
    driver-class-name: org.postgresql.Driver
    username: neondb_owner
    password: npg_T0whsOpagFJ9
    hikari:
      maximum-pool-size: 10
      connection-timeout: 30000
  sql:
    init:
      mode: never   # Scripts devem ser executados manualmente no PostgreSQL
```

### 3. **Schema Adaptado para PostgreSQL** (`schema.sql`)
- ✅ Mudado `AUTO_INCREMENT` → `BIGSERIAL` (padrão PostgreSQL)
- ✅ Sintaxe compatível com PostgreSQL

---

## 🚀 Como Configurar o Banco de Dados

### **Opção 1: Usando psql (CLI do PostgreSQL)**

```bash
# Executar o script de setup
psql "postgresql://neondb_owner:npg_T0whsOpagFJ9@ep-rapid-sea-ad4pirrg-pooler.c-2.us-east-1.aws.neon.tech/neondb?sslmode=require" -f setup-postgresql.sql
```

### **Opção 2: Usando o Console Web do Neon**

1. Acesse: https://console.neon.tech/
2. Navegue até o seu projeto
3. Abra o **SQL Editor**
4. Copie e cole o conteúdo de `setup-postgresql.sql`
5. Execute o script

### **Opção 3: Usando DBeaver, DataGrip, etc.**

1. Conecte ao banco com as credenciais:
   - **Host:** `ep-rapid-sea-ad4pirrg-pooler.c-2.us-east-1.aws.neon.tech`
   - **Database:** `neondb`
   - **User:** `neondb_owner`
   - **Password:** `npg_T0whsOpagFJ9`
   - **SSL Mode:** `require`
   - **Port:** `5432` (padrão)

2. Execute o script `setup-postgresql.sql`

---

## 📊 Verificação

Após executar o script, você deve ver:

```
tabela        | registros
--------------|----------
clientes      | 3
ingredientes  | 8
receitas      | 3
produtos      | 3
cardapios     | 1
pedidos       | 0
```

---

## 🎯 Testando a API

### 1. Iniciar a aplicação

```bash
cd /home/pinho/Documentos/projeto\&arq/TF/ipitsa
./mvnw spring-boot:run
```

### 2. Acessar a documentação

```
http://localhost:8080
```

### 3. Testar endpoints

```bash
# Listar cardápios
curl http://localhost:8080/cardapio

# Buscar cardápio por ID
curl http://localhost:8080/cardapio/1

# Registrar cliente
curl -X POST http://localhost:8080/cliente/registrar \
  -H "Content-Type: application/json" \
  -d '{
    "cpf": "12345678900",
    "nome": "João Silva",
    "celular": "51999887766",
    "endereco": "Rua das Flores, 123",
    "email": "joao.silva@email.com"
  }'

# Autenticar cliente
curl -X POST http://localhost:8080/cliente/autenticar \
  -H "Content-Type: application/json" \
  -d '{"cpf": "9001"}'

# Criar pedido
curl -X POST http://localhost:8080/pedido \
  -H "Content-Type: application/json" \
  -d '{
    "clienteCpf": "9001",
    "itens": [
      {"produtoId": 1, "quantidade": 2},
      {"produtoId": 3, "quantidade": 1}
    ]
  }'

# Consultar status do pedido
curl http://localhost:8080/pedido/1/status
```

---

## 🔄 Voltando para H2 (Desenvolvimento Local)

Se quiser voltar para H2 temporariamente:

```yaml
spring:
  datasource:
    url: jdbc:h2:mem:pizzadb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE
    driver-class-name: org.h2.Driver
    username: sa
    password:
  sql:
    init:
      mode: always
    schema-locations: classpath:schema.sql
    data-locations: classpath:data.sql
  h2:
    console:
      enabled: true
      path: /h2
```

**Importante:** Lembre-se de mudar `BIGSERIAL` de volta para `AUTO_INCREMENT` no `schema.sql` se usar H2!

---

## 📝 Notas Importantes

1. **Segurança:** As credenciais estão hardcoded no `application.yaml`. Para produção, use variáveis de ambiente:

```yaml
spring:
  datasource:
    url: ${DATABASE_URL}
    username: ${DATABASE_USERNAME}
    password: ${DATABASE_PASSWORD}
```

2. **Script Idempotente:** O script `setup-postgresql.sql` usa:
   - `DROP TABLE IF EXISTS` (limpa tabelas antigas)
   - `ON CONFLICT DO NOTHING` (evita duplicação de dados)
   - Pode ser executado múltiplas vezes com segurança

3. **Preços:** Os preços estão em centavos (5500 = R$ 55,00)

4. **IDs:** Os IDs são manuais para ingredientes, receitas, produtos e cardápios. Apenas `pedidos` usa auto-increment.

---

## 🎉 Status Atual

✅ Aplicação conectada ao PostgreSQL Neon  
✅ Index.html servindo a documentação  
✅ Todos os endpoints funcionais  
✅ Serviços FAKE integrados (pagamento, estoque, entregas)  
✅ Calculadoras flexíveis (impostos, descontos)  

---

## 🐛 Troubleshooting

### Erro: "connection refused"
- Verifique se o Neon está online
- Confirme as credenciais

### Erro: "relation already exists"
- Execute a parte `DROP TABLE` do script primeiro
- Ou use `ON CONFLICT` nas INSERTs

### Erro: "syntax error near AUTO_INCREMENT"
- Você ainda está usando a sintaxe do H2
- Use `BIGSERIAL` no PostgreSQL

---

## 📞 Suporte

- **Neon Console:** https://console.neon.tech/
- **Documentação Neon:** https://neon.tech/docs/
- **PostgreSQL Docs:** https://www.postgresql.org/docs/

---

**Desenvolvido com DDD + Clean Architecture** 🏗️
