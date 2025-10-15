package com.pinho.ipitsa.Aplicacao.Responses;

import java.util.List;

import com.pinho.ipitsa.Dominio.Entidades.Cardapio;
import com.pinho.ipitsa.Dominio.Entidades.Produto;

public class CardapioDetalhadoResponse {
    private Cardapio cardapio;
    private List<Produto> sugestoesDoChef;
    
    public CardapioDetalhadoResponse(Cardapio cardapio, List<Produto> sugestoesDoChef) {
        this.cardapio = cardapio;
        this.sugestoesDoChef = sugestoesDoChef;
    }

    public Cardapio getCardapio() {
        return cardapio;
    }

    public void setCardapio(Cardapio cardapio) {
        this.cardapio = cardapio;
    }

    public List<Produto> getSugestoesDoChef() {
        return sugestoesDoChef;
    }

    public void setSugestoesDoChef(List<Produto> sugestoesDoChef) {
        this.sugestoesDoChef = sugestoesDoChef;
    }
}
