package com.pinho.ipitsa.Aplicacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Aplicacao.Responses.AutenticacaoResponse;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Servicos.ClienteService;

@Component
public class AutenticarClienteUC {
    private ClienteService clienteService;

    @Autowired
    public AutenticarClienteUC(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    public AutenticacaoResponse run(String cpf) {
        try {
            Cliente cliente = clienteService.autenticarCliente(cpf);
            return new AutenticacaoResponse(cliente, true, "Autenticação realizada com sucesso");
        } catch (IllegalArgumentException e) {
            return new AutenticacaoResponse(null, false, e.getMessage());
        }
    }
}
