package com.pinho.ipitsa.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;
import com.pinho.ipitsa.Dominio.Servicos.PedidoService;

@Component
public class PagarPedidoUC {
    private PedidoService pedidoService;

    @Autowired
    public PagarPedidoUC(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public PedidoResponse run(Long id) {
        Pedido pedidoPago = pedidoService.atualizarStatusPedido(id, Pedido.Status.PAGO);
        return new PedidoResponse(pedidoPago);
    }
}
