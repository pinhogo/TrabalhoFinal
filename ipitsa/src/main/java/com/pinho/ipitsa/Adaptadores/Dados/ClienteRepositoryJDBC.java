package com.pinho.ipitsa.Adaptadores.Dados;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Dominio.Dados.ClienteRepository;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;

@Component
public class ClienteRepositoryJDBC implements ClienteRepository {
    
    private final JdbcTemplate jdbcTemplate;

    public ClienteRepositoryJDBC(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Cliente salvar(Cliente cliente) {
        String sql = "MERGE INTO clientes (cpf, nome, celular, endereco, email) " +
                     "KEY (cpf) VALUES (?, ?, ?, ?, ?)";
        
        jdbcTemplate.update(sql, 
            cliente.getCpf(),
            cliente.getNome(),
            cliente.getCelular(),
            cliente.getEndereco(),
            cliente.getEmail()
        );
        
        return cliente;
    }

    @Override
    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT cpf, nome, celular, endereco, email FROM clientes WHERE cpf = ?";
        
        try {
            return jdbcTemplate.queryForObject(sql, 
                (rs, rowNum) -> new Cliente(
                    rs.getString("cpf"),
                    rs.getString("nome"),
                    rs.getString("celular"),
                    rs.getString("endereco"),
                    rs.getString("email")
                ),
                cpf
            );
        } catch (Exception e) {
            return null; // Cliente não encontrado
        }
    }
}
