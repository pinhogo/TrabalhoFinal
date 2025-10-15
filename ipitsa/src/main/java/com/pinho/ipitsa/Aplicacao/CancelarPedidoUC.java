package com.pinho.ipitsa.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;
import com.pinho.ipitsa.Dominio.Servicos.PedidoService;

@Component
public class CancelarPedidoUC {
    private PedidoService pedidoService;

    @Autowired
    public CancelarPedidoUC(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    public PedidoResponse run(Long id) {
        Pedido pedidoCancelado = pedidoService.cancelarPedido(id);
        return new PedidoResponse(pedidoCancelado);
    }
}
