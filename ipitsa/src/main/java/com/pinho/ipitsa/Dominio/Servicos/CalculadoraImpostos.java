package com.pinho.ipitsa.Dominio.Servicos;

import org.springframework.stereotype.Service;

/**
 * Serviço de cálculo de impostos com fórmula flexível.
 * Pode ser facilmente alterado para diferentes estratégias de cálculo.
 */
@Service
public class CalculadoraImpostos {
    
    // Percentual de imposto (pode ser alterado facilmente)
    private static final double PERCENTUAL_IMPOSTO = 0.10; // 10%
    
    /**
     * Calcula o imposto sobre um valor.
     * Fórmula atual: valor * 10%
     * 
     * @param valor Valor base para cálculo
     * @return Valor do imposto calculado
     */
    public double calcularImposto(double valor) {
        return valor * PERCENTUAL_IMPOSTO;
    }
    
    /**
     * Calcula imposto com percentual personalizado.
     * Útil para promoções ou categorias especiais.
     */
    public double calcularImpostoComPercentual(double valor, double percentual) {
        return valor * percentual;
    }
    
    /**
     * Calcula imposto progressivo (exemplo de estratégia alternativa).
     * Valores maiores pagam mais imposto.
     */
    public double calcularImpostoProgressivo(double valor) {
        if (valor < 5000) { // Menos de R$ 50
            return valor * 0.05; // 5%
        } else if (valor < 10000) { // Entre R$ 50 e R$ 100
            return valor * 0.08; // 8%
        } else {
            return valor * 0.12; // 12%
        }
    }
}
