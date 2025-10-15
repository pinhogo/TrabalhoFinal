package com.pinho.ipitsa.Dominio.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinho.ipitsa.Dominio.Dados.ClienteRepository;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;

@Service
public class ClienteService {
    private ClienteRepository clienteRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /**
     * Registra um novo cliente no sistema
     * Valida se CPF já existe
     */
    public Cliente registrarCliente(String cpf, String nome, String celular, String endereco, String email) {
        // Verifica se cliente já existe
        Cliente clienteExistente = clienteRepository.buscarPorCpf(cpf);
        if (clienteExistente != null) {
            throw new IllegalArgumentException("Cliente com CPF " + cpf + " já está registrado");
        }

        // Cria novo cliente
        Cliente novoCliente = new Cliente(cpf, nome, celular, endereco, email);
        
        // Salva no banco
        return clienteRepository.salvar(novoCliente);
    }

    /**
     * Autentica um cliente pelo CPF
     * Por enquanto apenas valida se o CPF existe
     * TODO: Adicionar senha/autenticação mais robusta futuramente
     */
    public Cliente autenticarCliente(String cpf) {
        Cliente cliente = clienteRepository.buscarPorCpf(cpf);
        
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado");
        }
        
        return cliente;
    }

    /**
     * Busca cliente por CPF
     */
    public Cliente buscarPorCpf(String cpf) {
        return clienteRepository.buscarPorCpf(cpf);
    }
}
