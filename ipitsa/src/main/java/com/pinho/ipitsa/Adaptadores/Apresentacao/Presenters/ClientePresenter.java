package com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters;

import com.pinho.ipitsa.Aplicacao.Responses.ClienteResponse;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;

public class ClientePresenter {
    private String cpf;
    private String nome;
    private String celular;
    private String endereco;
    private String email;

    public ClientePresenter(ClienteResponse response) {
        Cliente cliente = response.getCliente();
        
        if (cliente != null) {
            this.cpf = cliente.getCpf();
            this.nome = cliente.getNome();
            this.celular = cliente.getCelular();
            this.endereco = cliente.getEndereco();
            this.email = cliente.getEmail();
        }
    }

    public String getCpf() {
        return cpf;
    }

    public String getNome() {
        return nome;
    }

    public String getCelular() {
        return celular;
    }

    public String getEndereco() {
        return endereco;
    }

    public String getEmail() {
        return email;
    }
}
