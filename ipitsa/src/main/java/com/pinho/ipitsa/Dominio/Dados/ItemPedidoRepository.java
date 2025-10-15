package com.pinho.ipitsa.Dominio.Dados;

import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;
import com.pinho.ipitsa.Dominio.Entidades.Produto;

public interface ItemPedidoRepository {
    ItemPedido adicionarItem(Produto item);
    ItemPedido RemoverItem(Produto item);
    ItemPedido atualizarQuantidade(Produto item, int novaQuantidade);
    double calcularTotal(ItemPedido item);

}
