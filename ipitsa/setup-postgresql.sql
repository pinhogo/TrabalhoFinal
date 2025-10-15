-- ===================================================================
-- SCRIPT DE SETUP COMPLETO PARA POSTGRESQL (Neon)
-- Execute este script uma única vez no seu banco PostgreSQL
-- ===================================================================

-- Limpar tabelas existentes (se necessário)
DROP TABLE IF EXISTS pedido_produto CASCADE;
DROP TABLE IF EXISTS pedidos CASCADE;
DROP TABLE IF EXISTS cardapio_produto CASCADE;
DROP TABLE IF EXISTS cardapios CASCADE;
DROP TABLE IF EXISTS produto_receita CASCADE;
DROP TABLE IF EXISTS produtos CASCADE;
DROP TABLE IF EXISTS receita_ingrediente CASCADE;
DROP TABLE IF EXISTS receitas CASCADE;
DROP TABLE IF EXISTS itensEstoque CASCADE;
DROP TABLE IF EXISTS ingredientes CASCADE;
DROP TABLE IF EXISTS clientes CASCADE;

-- ===================================================================
-- CRIAÇÃO DAS TABELAS
-- ===================================================================

-- Tabela de Clientes
CREATE TABLE IF NOT EXISTS clientes(
  cpf VARCHAR(15) NOT NULL PRIMARY KEY,
  nome VARCHAR(100) NOT NULL,
  celular VARCHAR(20) NOT NULL,
  endereco VARCHAR(255) NOT NULL,
  email VARCHAR(255) NOT NULL
);

-- Tabela de Ingredientes
CREATE TABLE IF NOT EXISTS ingredientes (
  id BIGINT PRIMARY KEY,
  descricao VARCHAR(255) NOT NULL
);

-- Tabela de Itens de Estoque
CREATE TABLE IF NOT EXISTS itensEstoque(
    id BIGINT PRIMARY KEY,
    quantidade INT,
    ingrediente_id BIGINT,
    FOREIGN KEY (ingrediente_id) REFERENCES ingredientes(id)
);

-- Tabela de Receitas
CREATE TABLE IF NOT EXISTS receitas (
  id BIGINT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL
);

-- Tabela de relacionamento entre Receita e Ingrediente
CREATE TABLE IF NOT EXISTS receita_ingrediente (
  receita_id BIGINT NOT NULL,
  ingrediente_id BIGINT NOT NULL,
  PRIMARY KEY (receita_id, ingrediente_id),
  FOREIGN KEY (receita_id) REFERENCES receitas(id),
  FOREIGN KEY (ingrediente_id) REFERENCES ingredientes(id)
);

-- Tabela de Produtos
CREATE TABLE IF NOT EXISTS produtos (
  id BIGINT PRIMARY KEY,
  descricao VARCHAR(255) NOT NULL,
  preco BIGINT
);

-- Tabela de relacionamento entre Produto e Receita
CREATE TABLE IF NOT EXISTS produto_receita (
  produto_id BIGINT NOT NULL,
  receita_id BIGINT NOT NULL,
  PRIMARY KEY (produto_id,receita_id),
  FOREIGN KEY (produto_id) REFERENCES produtos(id),
  FOREIGN KEY (receita_id) REFERENCES receitas(id)
);

-- Tabela de Cardápios
CREATE TABLE IF NOT EXISTS cardapios (
  id BIGINT PRIMARY KEY,
  titulo VARCHAR(255) NOT NULL
);

-- Tabela de relacionamento entre Cardápio e Produto
CREATE TABLE IF NOT EXISTS cardapio_produto (
  cardapio_id BIGINT NOT NULL,
  produto_id BIGINT NOT NULL,
  PRIMARY KEY (cardapio_id,produto_id),
  FOREIGN KEY (cardapio_id) REFERENCES cardapios(id),
  FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- Tabela de Pedidos (com BIGSERIAL para auto-increment no PostgreSQL)
CREATE TABLE IF NOT EXISTS pedidos (
  id BIGSERIAL PRIMARY KEY,
  cliente_cpf VARCHAR(15) NOT NULL,
  data_pedido TIMESTAMP NOT NULL,
  status VARCHAR(20) NOT NULL,
  valor DECIMAL(10,2) NOT NULL,
  imposto DECIMAL(10,2) NOT NULL,
  desconto DECIMAL(10,2) NOT NULL,
  total DECIMAL(10,2) NOT NULL,
  FOREIGN KEY (cliente_cpf) REFERENCES clientes(cpf)
);

-- Tabela de relacionamento entre Pedido e Produto
CREATE TABLE IF NOT EXISTS pedido_produto (
  pedido_id BIGINT NOT NULL,
  produto_id BIGINT NOT NULL,
  quantidade INT NOT NULL,
  PRIMARY KEY (pedido_id,produto_id),
  FOREIGN KEY (pedido_id) REFERENCES pedidos(id),
  FOREIGN KEY (produto_id) REFERENCES produtos(id)
);

-- ===================================================================
-- DADOS INICIAIS
-- ===================================================================

-- Clientes
INSERT INTO clientes (cpf, nome, celular, endereco, email) 
VALUES 
  ('9001', 'Huguinho', '51 99999-0001', 'Av Ipiranga 1000', 'huguinho@disney.com'),
  ('9002', 'Zezinho', '51 99999-0002', 'Av Ipiranga 2000', 'zezinho@disney.com'),
  ('9003', 'Luizinho', '51 99999-0003', 'Av Ipiranga 3000', 'luizinho@disney.com')
ON CONFLICT (cpf) DO NOTHING;

-- Ingredientes
INSERT INTO ingredientes (id, descricao) 
VALUES 
  (1, 'Molho de tomate'),
  (2, 'Mussarela'),
  (3, 'Calabresa'),
  (4, 'Presunto'),
  (5, 'Queijo'),
  (6, 'Bacon'),
  (7, 'Cebola'),
  (8, 'Azeitona')
ON CONFLICT (id) DO NOTHING;

-- Receitas
INSERT INTO receitas (id, titulo) 
VALUES 
  (1, 'Receita pizza calabresa'),
  (2, 'Receita pizza margherita'),
  (3, 'Receita pizza queijo/presunto')
ON CONFLICT (id) DO NOTHING;

-- Relacionamento Receita-Ingrediente
INSERT INTO receita_ingrediente (receita_id, ingrediente_id) 
VALUES 
  (1, 1), (1, 2), (1, 3), (1, 7), (1, 8),  -- Calabresa
  (2, 1), (2, 2),                           -- Margherita
  (3, 1), (3, 4), (3, 5)                    -- Queijo/Presunto
ON CONFLICT DO NOTHING;

-- Produtos
INSERT INTO produtos (id, descricao, preco) 
VALUES 
  (1, 'Pizza calabresa', 5500),
  (2, 'Pizza margherita', 4500),
  (3, 'Pizza queijo/presunto', 6000)
ON CONFLICT (id) DO NOTHING;

-- Relacionamento Produto-Receita
INSERT INTO produto_receita (produto_id, receita_id) 
VALUES 
  (1, 1),  -- Pizza calabresa usa receita 1
  (2, 2),  -- Pizza margherita usa receita 2
  (3, 3)   -- Pizza queijo/presunto usa receita 3
ON CONFLICT DO NOTHING;

-- Cardápios
INSERT INTO cardapios (id, titulo) 
VALUES 
  (1, 'Cardapio de Agosto')
ON CONFLICT (id) DO NOTHING;

-- Relacionamento Cardápio-Produto
INSERT INTO cardapio_produto (cardapio_id, produto_id) 
VALUES 
  (1, 1),  -- Calabresa no cardápio 1
  (1, 2),  -- Margherita no cardápio 1
  (1, 3)   -- Queijo/Presunto no cardápio 1
ON CONFLICT DO NOTHING;

-- Itens de Estoque (simulação)
INSERT INTO itensEstoque (id, quantidade, ingrediente_id) 
VALUES 
  (1, 100, 1),  -- 100 unidades de molho
  (2, 100, 2),  -- 100 unidades de mussarela
  (3, 100, 3),  -- 100 unidades de calabresa
  (4, 100, 4),  -- 100 unidades de presunto
  (5, 100, 5),  -- 100 unidades de queijo
  (6, 100, 6),  -- 100 unidades de bacon
  (7, 100, 7),  -- 100 unidades de cebola
  (8, 100, 8)   -- 100 unidades de azeitona
ON CONFLICT (id) DO NOTHING;

-- ===================================================================
-- VERIFICAÇÃO
-- ===================================================================

-- Mostrar contagem de registros
SELECT 'clientes' AS tabela, COUNT(*) AS registros FROM clientes
UNION ALL
SELECT 'ingredientes', COUNT(*) FROM ingredientes
UNION ALL
SELECT 'receitas', COUNT(*) FROM receitas
UNION ALL
SELECT 'produtos', COUNT(*) FROM produtos
UNION ALL
SELECT 'cardapios', COUNT(*) FROM cardapios
UNION ALL
SELECT 'pedidos', COUNT(*) FROM pedidos;

-- ===================================================================
-- FIM DO SCRIPT
-- ===================================================================
