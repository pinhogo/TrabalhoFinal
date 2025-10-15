package com.pinho.ipitsa.Adaptadores.Dados;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Dominio.Dados.EstoqueRepository;
import com.pinho.ipitsa.Dominio.Entidades.ItemEstoque;

@Component
public class EstoqueRepositoryJDBC implements EstoqueRepository {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public EstoqueRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int quantidadeTotal(ItemEstoque item) {
        long ingredienteId = item.getIngrediente().getId();
        String sql = "SELECT quantidade FROM itensEstoque WHERE ingrediente_id = ?";
        
        // Executa a query e retorna a quantidade
        Integer quantidade = jdbcTemplate.queryForObject(sql, Integer.class, ingredienteId);
        
        // Retorna 0 se não encontrar nada
        return quantidade != null ? quantidade : 0;
    }

    @Override
    public void atualizar(ItemEstoque item) {
        String sql = "UPDATE itensEstoque SET quantidade = ? WHERE ingrediente_id = ?";
        
        jdbcTemplate.update(sql, 
            item.getQuantidade(), 
            item.getIngrediente().getId()
        );
    }
}