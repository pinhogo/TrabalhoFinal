package com.pinho.ipitsa.Dominio.Entidades;

import java.util.List;
//implementado
public class Estoque {

    List<ItemEstoque> itens;

    public Estoque(List<ItemEstoque> itens) {
        this.itens = itens;
    }

    public List<ItemEstoque> getItens() {
        return itens;
    }

    public void setItens(List<ItemEstoque> itens) {
        this.itens = itens;
    }
}
