package com.pinho.ipitsa.Dominio.Dados;

import com.pinho.ipitsa.Dominio.Entidades.ItemEstoque;

public interface EstoqueRepository {
    int quantidadeTotal(ItemEstoque item);
    void atualizar(ItemEstoque item);
}
