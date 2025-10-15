package com.pinho.ipitsa.Dominio.Entidades;

public class ItemPedido {
    private Produto item;
    private int quantidade;

    public ItemPedido(Produto item, int quantidade) {
        this.item = item;
        this.quantidade = quantidade;
    }

    public Produto getItem() { return item; }
    public int getQuantidade() { return quantidade; }
    public double getPrecoTotal() { return item.getPreco() * quantidade; }
}
