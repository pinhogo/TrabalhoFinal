package com.pinho.ipitsa.Dominio.Dados;

import java.util.List;

import com.pinho.ipitsa.Dominio.Entidades.Ingrediente;

public interface IngredientesRepository {
    List<Ingrediente> recuperaTodos();
    List<Ingrediente> recuperaIngredientesReceita(long id);
}
