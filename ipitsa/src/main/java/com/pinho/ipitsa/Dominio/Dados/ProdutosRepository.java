package com.pinho.ipitsa.Dominio.Dados;

import java.util.List;

import com.pinho.ipitsa.Dominio.Entidades.Produto;

public interface ProdutosRepository {
    Produto recuperaProdutoPorid(long id);
    List<Produto> recuperaProdutosCardapio(long id);
}
