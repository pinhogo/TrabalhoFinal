package com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters;

import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;

public class PedidoPresenter {
    private Long id;
    private String clienteCpf;
    private String status;
    private Double valor;
    private Integer quantidadeItens;

    public PedidoPresenter(PedidoResponse response) {
        Pedido pedido = response.getPedido();
        
        if (pedido != null) {
            this.id = pedido.getId();
            this.clienteCpf = pedido.getCliente().getCpf();
            this.status = pedido.getStatus().toString();
            this.valor = pedido.getValorCobrado();
            this.quantidadeItens = pedido.getItens() != null ? pedido.getItens().size() : 0;
        }
    }

    public Long getId() {
        return id;
    }

    public String getClienteCpf() {
        return clienteCpf;
    }

    public String getStatus() {
        return status;
    }

    public Double getValor() {
        return valor;
    }

    public Integer getQuantidadeItens() {
        return quantidadeItens;
    }
}
