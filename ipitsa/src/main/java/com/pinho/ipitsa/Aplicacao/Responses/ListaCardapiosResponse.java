package com.pinho.ipitsa.Aplicacao.Responses;

import java.util.List;

import com.pinho.ipitsa.Dominio.Entidades.CabecalhoCardapio;
import com.pinho.ipitsa.Dominio.Entidades.Produto;

public class ListaCardapiosResponse {
    private List<CabecalhoCardapio> cardapios;
    private List<Produto> sugestoesDoChef;
    
    public ListaCardapiosResponse(List<CabecalhoCardapio> cardapios, List<Produto> sugestoesDoChef) {
        this.cardapios = cardapios;
        this.sugestoesDoChef = sugestoesDoChef;
    }

    public List<CabecalhoCardapio> getCardapios() {
        return cardapios;
    }

    public void setCardapios(List<CabecalhoCardapio> cardapios) {
        this.cardapios = cardapios;
    }

    public List<Produto> getSugestoesDoChef() {
        return sugestoesDoChef;
    }

    public void setSugestoesDoChef(List<Produto> sugestoesDoChef) {
        this.sugestoesDoChef = sugestoesDoChef;
    }
}
