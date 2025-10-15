package com.pinho.ipitsa.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.ClienteResponse;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Servicos.ClienteService;

@Component
public class RegistrarClienteUC {
    private ClienteService clienteService;

    @Autowired
    public RegistrarClienteUC(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public ClienteResponse run(String cpf, String nome, String celular, String endereco, String email) {
        Cliente cliente = clienteService.registrarCliente(cpf, nome, celular, endereco, email);
        return new ClienteResponse(cliente);
    }
}
