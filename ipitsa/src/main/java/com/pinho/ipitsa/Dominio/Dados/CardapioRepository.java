package com.pinho.ipitsa.Dominio.Dados;

import java.util.List;

import com.pinho.ipitsa.Dominio.Entidades.CabecalhoCardapio;
import com.pinho.ipitsa.Dominio.Entidades.Cardapio;
import com.pinho.ipitsa.Dominio.Entidades.Produto;

public interface CardapioRepository {
    List<CabecalhoCardapio> cardapiosDisponiveis();
    Cardapio recuperaPorId(long id);
    List<Produto> indicacoesDoChef();
}
