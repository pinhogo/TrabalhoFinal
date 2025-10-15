package com.pinho.ipitsa.Aplicacao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.CardapioResponse;
import com.pinho.ipitsa.Dominio.Entidades.CabecalhoCardapio;
import com.pinho.ipitsa.Dominio.Entidades.Cardapio;
import com.pinho.ipitsa.Dominio.Entidades.Produto;
import com.pinho.ipitsa.Dominio.Servicos.CardapioService;

@Component
public class RecuperarCardapioUC {
    private CardapioService cardapioService;

    @Autowired
    public RecuperarCardapioUC(CardapioService cardapioService){
        this.cardapioService = cardapioService;
    }

    public CardapioResponse run(long idCardapio){
        Cardapio cardapio = cardapioService.recuperaCardapio(idCardapio);
        List<Produto> sugestoes = cardapioService.recuperaSugestoesDoChef();
        return new CardapioResponse(cardapio,sugestoes);
    }
}
