package com.pinho.ipitsa.Dominio.Dados;

import com.pinho.ipitsa.Dominio.Entidades.Cliente;

public interface ClienteRepository {
    Cliente salvar(Cliente cliente);
    Cliente buscarPorCpf(String cpf);
}
