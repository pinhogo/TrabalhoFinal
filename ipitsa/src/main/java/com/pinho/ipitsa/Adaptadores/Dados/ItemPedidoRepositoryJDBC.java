package com.pinho.ipitsa.Adaptadores.Dados;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinho.ipitsa.Dominio.Dados.ItemPedidoRepository;
import com.pinho.ipitsa.Dominio.Dados.ProdutosRepository;
import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;
import com.pinho.ipitsa.Dominio.Entidades.Produto;

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
        // Cria um ItemPedido com quantidade padrão 1
        return new ItemPedido(item, 1);
    }

    @Override
    public ItemPedido RemoverItem(Produto item) {
        // Retorna null para indicar remoção
        return null;
    }

    @Override
    public ItemPedido atualizarQuantidade(Produto item, int novaQuantidade) {
        // Cria novo ItemPedido com quantidade atualizada
        return new ItemPedido(item, novaQuantidade);
    }

    @Override
    public double calcularTotal(ItemPedido item) {
        // Retorna o preço total do item
        return item.getPrecoTotal();
    }
}
