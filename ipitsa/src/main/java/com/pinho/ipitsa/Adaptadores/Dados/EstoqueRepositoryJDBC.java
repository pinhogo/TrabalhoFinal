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
        
        try {
            // Executa a query e retorna a quantidade
            Integer quantidade = jdbcTemplate.queryForObject(sql, Integer.class, ingredienteId);
            // Retorna 0 se não encontrar nada
            return quantidade != null ? quantidade : 0;
        } catch (Exception e) {
            // Se não encontrar, retorna 0
            return 0;
        }
    }

    @Override
    public void atualizar(ItemEstoque item) {
        String sql = "UPDATE itensEstoque SET quantidade = ? WHERE ingrediente_id = ?";
        
        int rowsAffected = jdbcTemplate.update(sql, 
            item.getQuantidade(), 
            item.getIngrediente().getId()
        );
        
        // Se não atualizou nenhuma linha, talvez precise inserir
        if (rowsAffected == 0) {
            inserir(item);
        }
    }

    // Métodos auxiliares úteis
    
    /**
     * Verifica se tem estoque suficiente de um ingrediente
     */
    public boolean temEstoqueSuficiente(long ingredienteId, int quantidadeNecessaria) {
        String sql = "SELECT quantidade FROM itensEstoque WHERE ingrediente_id = ?";
        try {
            Integer quantidade = jdbcTemplate.queryForObject(sql, Integer.class, ingredienteId);
            return quantidade != null && quantidade >= quantidadeNecessaria;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Consome uma quantidade do estoque
     */
    public void consumirEstoque(long ingredienteId, int quantidade) {
        String sql = "UPDATE itensEstoque SET quantidade = quantidade - ? WHERE ingrediente_id = ?";
        jdbcTemplate.update(sql, quantidade, ingredienteId);
    }

    /**
     * Adiciona quantidade ao estoque
     */
    public void adicionarEstoque(long ingredienteId, int quantidade) {
        String sql = "UPDATE itensEstoque SET quantidade = quantidade + ? WHERE ingrediente_id = ?";
        jdbcTemplate.update(sql, quantidade, ingredienteId);
    }

    /**
     * Insere um novo item no estoque (se não existir)
     */
    private void inserir(ItemEstoque item) {
        // Busca um ID disponível
        String sqlId = "SELECT COALESCE(MAX(id), 0) + 1 FROM itensEstoque";
        Long novoId = jdbcTemplate.queryForObject(sqlId, Long.class);
        
        String sql = "INSERT INTO itensEstoque (id, quantidade, ingrediente_id) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, 
            novoId,
            item.getQuantidade(), 
            item.getIngrediente().getId()
        );
    }

    /**
     * Busca a quantidade disponível de um ingrediente específico
     */
    public int buscarQuantidadePorIngredienteId(long ingredienteId) {
        String sql = "SELECT quantidade FROM itensEstoque WHERE ingrediente_id = ?";
        try {
            Integer quantidade = jdbcTemplate.queryForObject(sql, Integer.class, ingredienteId);
            return quantidade != null ? quantidade : 0;
        } catch (Exception e) {
            return 0;
        }
    }
}