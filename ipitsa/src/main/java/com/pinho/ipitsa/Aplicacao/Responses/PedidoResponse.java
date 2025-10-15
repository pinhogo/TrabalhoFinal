package com.pinho.ipitsa.Aplicacao.Responses;

import com.pinho.ipitsa.Dominio.Entidades.Pedido;

public class PedidoResponse {
    private Pedido pedido;

    public PedidoResponse(Pedido pedido) {
        this.pedido = pedido;
    }

    public Pedido getPedido() {
        return pedido;
    }
}
