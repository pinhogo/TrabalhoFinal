package com.pinho.ipitsa.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Dados.PedidoRepository;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;

@Component
public class ConsultarStatusPedidoUC {
    private PedidoRepository pedidoRepository;

    @Autowired
    public ConsultarStatusPedidoUC(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public PedidoResponse run(Long id) {
        Pedido pedido = pedidoRepository.buscarPorId(id);
        return new PedidoResponse(pedido);
    }
}
