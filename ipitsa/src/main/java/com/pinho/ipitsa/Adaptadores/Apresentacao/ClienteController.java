package com.pinho.ipitsa.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters.AutenticacaoPresenter;
import com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters.ClientePresenter;
import com.pinho.ipitsa.Aplicacao.AutenticarClienteUC;
import com.pinho.ipitsa.Aplicacao.RegistrarClienteUC;
import com.pinho.ipitsa.Aplicacao.Responses.AutenticacaoResponse;
import com.pinho.ipitsa.Aplicacao.Responses.ClienteResponse;

@RestController
@RequestMapping("/cliente")
public class ClienteController {
    
    private RegistrarClienteUC registrarClienteUC;
    private AutenticarClienteUC autenticarClienteUC;

    public ClienteController(
        RegistrarClienteUC registrarClienteUC,
        AutenticarClienteUC autenticarClienteUC
    ) {
        this.registrarClienteUC = registrarClienteUC;
        this.autenticarClienteUC = autenticarClienteUC;
    }

    /**
     * POST /cliente/registrar
     * Registra um novo cliente no sistema
     */
    @PostMapping("/registrar")
    @CrossOrigin("*")
    public ClientePresenter registrarCliente(@RequestBody RegistroRequest request) {
        ClienteResponse response = registrarClienteUC.run(
            request.getCpf(),
            request.getNome(),
            request.getCelular(),
            request.getEndereco(),
            request.getEmail()
        );
        return new ClientePresenter(response);
    }

    /**
     * POST /cliente/autenticar
     * Autentica um cliente pelo CPF
     */
    @PostMapping("/autenticar")
    @CrossOrigin("*")
    public AutenticacaoPresenter autenticarCliente(@RequestBody AutenticacaoRequest request) {
        AutenticacaoResponse response = autenticarClienteUC.run(request.getCpf());
        return new AutenticacaoPresenter(response);
    }

    // DTOs para receber dados
    public static class RegistroRequest {
        private String cpf;
        private String nome;
        private String celular;
        private String endereco;
        private String email;

        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
        
        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }
        
        public String getCelular() { return celular; }
        public void setCelular(String celular) { this.celular = celular; }
        
        public String getEndereco() { return endereco; }
        public void setEndereco(String endereco) { this.endereco = endereco; }
        
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
    }

    public static class AutenticacaoRequest {
        private String cpf;

        public String getCpf() { return cpf; }
        public void setCpf(String cpf) { this.cpf = cpf; }
    }
}
