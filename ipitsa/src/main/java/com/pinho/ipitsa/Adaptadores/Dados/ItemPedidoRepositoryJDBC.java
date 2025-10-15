package com.pinho.ipitsa.Adaptadores.Dados;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinho.ipitsa.Dominio.Dados.ItemPedidoRepository;
import com.pinho.ipitsa.Dominio.Dados.ProdutosRepository;

@Component
public class ItemPedidoRepositoryJDBC implements ItemPedidoRepository {
    private JdbcTemplate jdbcTemplate;
    private ProdutosRepository produtosRepository;

    public ItemPedidoRepositoryJDBC(JdbcTemplate jdbcTemplate, ProdutosRepository produtosRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.produtosRepository = produtosRepository;
    }

    @Override
    public ItemPedido adicionarItem(Produto item) {
        String sql = "INSERT INTO item_pedido (produto_id, quantidade) VALUES (?, ?)";
        jdbcTemplate.update(sql, item.getItem().getId(), item.getQuantidade());
        return item;
    }


}
