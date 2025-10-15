package com.pinho.ipitsa.Dominio.Dados;

import com.pinho.ipitsa.Dominio.Entidades.Receita;

public interface ReceitasRepository {
    Receita recuperaReceita(long id);
    
}
