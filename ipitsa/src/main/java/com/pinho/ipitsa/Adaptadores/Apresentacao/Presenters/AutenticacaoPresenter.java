package com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters;

import com.pinho.ipitsa.Aplicacao.Responses.AutenticacaoResponse;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;

public class AutenticacaoPresenter {
    private boolean autenticado;
    private String mensagem;
    private String cpf;
    private String nome;

    public AutenticacaoPresenter(AutenticacaoResponse response) {
        this.autenticado = response.isAutenticado();
        this.mensagem = response.getMensagem();
        
        Cliente cliente = response.getCliente();
        if (cliente != null) {
            this.cpf = cliente.getCpf();
            this.nome = cliente.getNome();
        }
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    public String getMensagem() {
        return mensagem;
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }
}
