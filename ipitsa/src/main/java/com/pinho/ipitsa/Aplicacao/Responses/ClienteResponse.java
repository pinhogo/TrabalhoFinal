package com.pinho.ipitsa.Aplicacao.Responses;

import com.pinho.ipitsa.Dominio.Entidades.Cliente;

public class ClienteResponse {
    private Cliente cliente;

    public ClienteResponse(Cliente cliente) {
        this.cliente = cliente;
    }

    public Cliente getCliente() {
        return cliente;
    }
}
