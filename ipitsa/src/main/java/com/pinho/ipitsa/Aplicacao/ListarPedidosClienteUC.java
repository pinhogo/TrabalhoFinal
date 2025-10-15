package com.pinho.ipitsa.Aplicacao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Dados.PedidoRepository;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;

@Component
public class ListarPedidosClienteUC {
    private PedidoRepository pedidoRepository;

    @Autowired
    public ListarPedidosClienteUC(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<PedidoResponse> run(String cpf, LocalDateTime dataInicio, LocalDateTime dataFim) {
        // Criar um objeto Cliente temporário só com o CPF para buscar
        Cliente cliente = new Cliente(cpf, "", "", "", "");
        
        List<Pedido> pedidos = pedidoRepository.buscarPorCliente(cliente);
        
        // Filtrar por status ENTREGUE e data de pagamento entre dataInicio e dataFim
        return pedidos.stream()
            .filter(p -> p.getStatus() == Pedido.Status.ENTREGUE)
            .filter(p -> p.getDataHoraPagamento() != null)
            .filter(p -> !p.getDataHoraPagamento().isBefore(dataInicio) && 
                        !p.getDataHoraPagamento().isAfter(dataFim))
            .map(PedidoResponse::new)
            .collect(Collectors.toList());
    }
}
