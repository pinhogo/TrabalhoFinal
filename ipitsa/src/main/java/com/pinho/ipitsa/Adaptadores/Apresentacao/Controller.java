package com.pinho.ipitsa.Adaptadores.Apresentacao;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class Controller {
    @GetMapping("/api")
    @CrossOrigin("*")
    public String welcomeMessage() {
        return "Bem Vindo a Pizzaria ECA - API REST";
    }

}
