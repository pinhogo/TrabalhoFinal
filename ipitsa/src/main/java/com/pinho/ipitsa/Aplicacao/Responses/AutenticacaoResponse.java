package com.pinho.ipitsa.Aplicacao.Responses;

import com.pinho.ipitsa.Dominio.Entidades.Cliente;

public class AutenticacaoResponse {
    private Cliente cliente;
    private boolean autenticado;
    private String mensagem;

    public AutenticacaoResponse(Cliente cliente, boolean autenticado, String mensagem) {
        this.cliente = cliente;
        this.autenticado = autenticado;
        this.mensagem = mensagem;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public String getMensagem() {
        return mensagem;
    }
}
