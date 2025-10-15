package com.pinho.ipitsa.Adaptadores.Dados;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Dominio.Dados.ProdutosRepository;
import com.pinho.ipitsa.Dominio.Dados.ReceitasRepository;
import com.pinho.ipitsa.Dominio.Entidades.Ingrediente;
import com.pinho.ipitsa.Dominio.Entidades.Produto;
import com.pinho.ipitsa.Dominio.Entidades.Receita;

@Component
public class ProdutosRepositoryJDBC implements ProdutosRepository {
    private JdbcTemplate jdbcTemplate;
    private ReceitasRepository receitasRepository;

    @Autowired
    public ProdutosRepositoryJDBC(JdbcTemplate jdbcTemplate,ReceitasRepository receitasRepository){
        this.jdbcTemplate = jdbcTemplate;
        this.receitasRepository = receitasRepository;
    }

    @Override
    public List<Produto> recuperaProdutosCardapio(long id) {
        String sql = "SELECT p.id, p.descricao, p.preco, pr.receita_id " +
                     "FROM produtos p " +
                     "JOIN cardapio_produto cp ON p.id = cp.produto_id " +
                     "JOIN produto_receita pr ON p.id = pr.produto_id " +
                     "WHERE cp.cardapio_id = ?";
        List<Produto> produtos = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> {
                long produtoId = rs.getLong("id");
                String descricao = rs.getString("descricao");
                int preco = rs.getInt("preco");
                long receitaId = rs.getLong("receita_id");
                Receita receita = receitasRepository.recuperaReceita(receitaId);
                return new Produto(produtoId, descricao, receita, preco);
            }
        );
        return produtos;
    }

    @Override
    public Produto recuperaProdutoPorid(long id) {
        String sql = "SELECT p.id, p.descricao, p.preco, pr.receita_id " +
                     "FROM produtos p " +
                     "JOIN produto_receita pr ON p.id = pr.produto_id " +
                     "WHERE p.id = ?";
        List<Produto> produtos = this.jdbcTemplate.query(
            sql,
            ps -> ps.setLong(1, id),
            (rs, rowNum) -> {
                long produtoId = rs.getLong("id");
                String descricao = rs.getString("descricao");
                int preco = rs.getInt("preco");
                long receitaId = rs.getLong("receita_id");
                Receita receita = receitasRepository.recuperaReceita(receitaId);
                return new Produto(produtoId, descricao, receita, preco);
            }
        );
        return produtos.isEmpty() ? null : produtos.getFirst();        
    }
    
}
