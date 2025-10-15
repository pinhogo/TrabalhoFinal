package com.pinho.ipitsa.Adaptadores.Dados;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Dominio.Dados.ClienteRepository;
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
    private ClienteRepository clienteRepository;

    @Autowired
    public PedidoRepositoryJDBC(JdbcTemplate jdbcTemplate, ProdutosRepository produtosRepository, ClienteRepository clienteRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.produtosRepository = produtosRepository;
        this.clienteRepository = clienteRepository;
    }

    @Override
    public Pedido salvar(Pedido pedido){
        // Se o pedido já tem ID (maior que 0), faz UPDATE
        if (pedido.getId() > 0) {
            String sql = "UPDATE pedidos SET cliente_cpf = ?, data_pedido = ?, status = ?, valor = ?, imposto = ?, desconto = ?, total = ? WHERE id = ?";
            
            jdbcTemplate.update(sql,
                pedido.getCliente().getCpf(),
                pedido.getDataHoraPagamento(),
                pedido.getStatus().toString(),
                pedido.getValor(),
                pedido.getImpostos(),
                pedido.getDesconto(),
                pedido.getValorCobrado(),
                pedido.getId()
            );
            
            return pedido;
        }
        
        String sql = "INSERT INTO pedidos (cliente_cpf, data_pedido, status, valor, imposto, desconto, total) VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, pedido.getCliente().getCpf());
            ps.setObject(2, pedido.getDataHoraPagamento());
            ps.setString(3, pedido.getStatus().toString());
            ps.setDouble(4, pedido.getValor());
            ps.setDouble(5, pedido.getImpostos());
            ps.setDouble(6, pedido.getDesconto());
            ps.setDouble(7, pedido.getValorCobrado());
            return ps;
        }, keyHolder);
        
        Long id = ((Number) keyHolder.getKeys().get("id")).longValue();
        return pedido.comId(id);
    }

    @Override
    public Pedido buscarPorId(long id) {
        String sql = "SELECT id, cliente_cpf, data_pedido, status, valor, imposto, desconto, total FROM pedidos WHERE id = ?";
        List<Pedido> pedidos = jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> {
                // Buscar cliente pelo CPF
                String cpf = rs.getString("cliente_cpf");
                Cliente cliente = clienteRepository.buscarPorCpf(cpf);
                
                if (cliente == null) {
                    throw new RuntimeException("Cliente não encontrado: " + cpf);
                }
                
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
        String sqlItens = "DELETE FROM pedido_produto WHERE pedido_id = ?";
        jdbcTemplate.update(sqlItens, id);
        
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
                // Buscar cliente pelo CPF
                String cpf = rs.getString("cliente_cpf");
                Cliente cliente = clienteRepository.buscarPorCpf(cpf);
                
                if (cliente == null) {
                    throw new RuntimeException("Cliente não encontrado: " + cpf);
                }
                
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
