package com.pinho.ipitsa.Adaptadores.Dados;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Dominio.Dados.PedidoRepository;
import com.pinho.ipitsa.Dominio.Dados.ProdutosRepository;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;
import com.pinho.ipitsa.Dominio.Entidades.Produto;

@Component
public class PedidoRepositoryJDBC implements PedidoRepository{
    private JdbcTemplate jdbcTemplate;
    private ProdutosRepository produtosRepository;

    @Autowired
    public PedidoRepositoryJDBC(JdbcTemplate jdbcTemplate, ProdutosRepository produtosRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.produtosRepository = produtosRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido){
        String sql = "INSERT INTO pedidos (cliente_cpf, data_pedido, status, valor, imposto, desconto, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        org.springframework.jdbc.support.KeyHolder keyHolder = new org.springframework.jdbc.support.GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            java.sql.PreparedStatement ps = connection.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pedido.getCliente().getCpf());
            ps.setObject(2, pedido.getDataHoraPagamento());
            ps.setString(3, pedido.getStatus().toString());
            ps.setDouble(4, pedido.getValor());
            ps.setDouble(5, pedido.getImpostos());
            ps.setDouble(6, pedido.getDesconto());
            ps.setDouble(7, pedido.getValorCobrado());
            return ps;
        }, keyHolder);
        
        // Recupera o ID gerado
        Long id = keyHolder.getKey().longValue();
        // Retorna uma NOVA instância com o ID (imutável)
        return pedido.comId(id);
    }

    @Override
    public Pedido buscarPorId(long id) {
        String sql = "SELECT id, cliente_cpf, data_pedido, status, valor, imposto, desconto, total FROM pedidos WHERE id = ?";
        List<Pedido> pedidos = jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> {
                // Buscar cliente pelo CPF (você pode implementar isso depois)
                Cliente cliente = null; // clienteRepository.buscarPorCpf(rs.getString("cliente_cpf"));
                
                // Buscar itens primeiro
                List<ItemPedido> itens = buscarItensDoPedido(rs.getLong("id"));
                
                // Cria pedido imutável com construtor
                return new Pedido(
                    rs.getLong("id"),
                    cliente,
                    rs.getTimestamp("data_pedido").toLocalDateTime(),
                    itens,
                    Pedido.Status.valueOf(rs.getString("status")),
                    rs.getDouble("valor"),
                    rs.getDouble("imposto"),
                    rs.getDouble("desconto"),
                    rs.getDouble("total")
                );
            }
        );
        
        return pedidos.isEmpty() ? null : pedidos.get(0);
    }

    @Override
    public double valorTotal(long id) {
        String sql = "SELECT total FROM pedidos WHERE id = ?";
        Double total = jdbcTemplate.queryForObject(sql, Double.class, id);
        return total != null ? total : 0.0;
    }

    @Override
    public void excluir(long id) {
        // Primeiro exclui os itens do pedido (relacionamento)
        String sqlItens = "DELETE FROM pedido_produto WHERE pedido_id = ?";
        jdbcTemplate.update(sqlItens, id);
        
        // Depois exclui o pedido
        String sql = "DELETE FROM pedidos WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    public List<Pedido> buscarPorCliente(Cliente cliente) {
        String sql = "SELECT id, cliente_cpf, data_pedido, status, valor, imposto, desconto, total FROM pedidos WHERE cliente_cpf = ?";
        List<Pedido> pedidos = jdbcTemplate.query(
            sql,
            ps -> ps.setString(1, cliente.getCpf()),
            (rs, rowNum) -> {
                List<ItemPedido> itens = buscarItensDoPedido(rs.getLong("id"));
                
                return new Pedido(
                    rs.getLong("id"),
                    cliente,
                    rs.getTimestamp("data_pedido").toLocalDateTime(),
                    itens,
                    Pedido.Status.valueOf(rs.getString("status")),
                    rs.getDouble("valor"),
                    rs.getDouble("imposto"),
                    rs.getDouble("desconto"),
                    rs.getDouble("total")
                );
            }
        );
        return pedidos;
    }

    @Override
    public List<Pedido> buscarPorStatus(Pedido.Status status) {
        String sql = "SELECT id, cliente_cpf, data_pedido, status, valor, imposto, desconto, total FROM pedidos WHERE status = ?";
        List<Pedido> pedidos = jdbcTemplate.query(
            sql,
            ps -> ps.setString(1, status.toString()),
            (rs, rowNum) -> {
                Cliente cliente = null; // Buscar depois se necessário
                List<ItemPedido> itens = buscarItensDoPedido(rs.getLong("id"));
                
                return new Pedido(
                    rs.getLong("id"),
                    cliente,
                    rs.getTimestamp("data_pedido").toLocalDateTime(),
                    itens,
                    Pedido.Status.valueOf(rs.getString("status")),
                    rs.getDouble("valor"),
                    rs.getDouble("imposto"),
                    rs.getDouble("desconto"),
                    rs.getDouble("total")
                );
            }
        );
        return pedidos;
    }

    @Override
    public List<ItemPedido> buscarItensDoPedido(long pedidoId) {
        String sql = "SELECT produto_id, quantidade FROM pedido_produto WHERE pedido_id = ?";
        List<ItemPedido> itens = jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, pedidoId),
            (rs, rowNum) -> {
                long produtoId = rs.getLong("produto_id");
                // Busca o produto completo usando o ProdutosRepository
                Produto produto = produtosRepository.recuperaProdutoPorid(produtoId);
                int quantidade = rs.getInt("quantidade");
                // Cria ItemPedido imutável com construtor
                return new ItemPedido(produto, quantidade);
            }
        );
        return itens;
    }
}
