package com.pinho.ipitsa.Aplicacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Dados.PedidoRepository;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;

@Component
public class ListarPedidosEntreguesUC {
    private PedidoRepository pedidoRepository;

    @Autowired
    public ListarPedidosEntreguesUC(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponse> run(LocalDateTime dataInicio, LocalDateTime dataFim) {
        List<Pedido> pedidos = pedidoRepository.buscarPorStatus(Pedido.Status.ENTREGUE);
        
        // Filtrar por data de pagamento entre dataInicio e dataFim
        return pedidos.stream()
            .filter(p -> p.getDataHoraPagamento() != null)
            .filter(p -> !p.getDataHoraPagamento().isBefore(dataInicio) && 
                        !p.getDataHoraPagamento().isAfter(dataFim))
            .map(PedidoResponse::new)
            .collect(Collectors.toList());
    }
}
