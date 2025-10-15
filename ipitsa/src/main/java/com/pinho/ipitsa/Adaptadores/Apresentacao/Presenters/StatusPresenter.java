package com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters;

import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;

public class StatusPresenter {
    private Long pedidoId;
    private String status;

    public StatusPresenter(PedidoResponse response) {
        Pedido pedido = response.getPedido();
        
        if (pedido != null) {
            this.pedidoId = pedido.getId();
            this.status = pedido.getStatus().toString();
        }
    }

    public Long getPedidoId() {
        return pedidoId;
    }

    public String getStatus() {
        return status;
    }
}
