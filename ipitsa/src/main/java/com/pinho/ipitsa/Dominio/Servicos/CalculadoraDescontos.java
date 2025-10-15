package com.pinho.ipitsa.Dominio.Servicos;

import org.springframework.stereotype.Service;

/**
 * Serviço de cálculo de descontos com fórmulas flexíveis.
 * Facilita mudanças frequentes nas regras de desconto.
 */
@Service
public class CalculadoraDescontos {
    
    // Valores mínimos para descontos
    private static final double VALOR_MINIMO_DESCONTO_5 = 10000; // R$ 100
    private static final double VALOR_MINIMO_DESCONTO_10 = 20000; // R$ 200
    
    /**
     * Calcula desconto baseado no valor total.
     * Fórmula atual:
     * - Sem desconto: valores abaixo de R$ 100
     * - 5% desconto: valores entre R$ 100 e R$ 200
     * - 10% desconto: valores acima de R$ 200
     * 
     * @param valor Valor base para cálculo
     * @return Valor do desconto calculado
     */
    public double calcularDesconto(double valor) {
        if (valor >= VALOR_MINIMO_DESCONTO_10) {
            return valor * 0.10; // 10% de desconto
        } else if (valor >= VALOR_MINIMO_DESCONTO_5) {
            return valor * 0.05; // 5% de desconto
        }
        return 0.0; // Sem desconto
    }
    
    /**
     * Calcula desconto com percentual fixo.
     * Útil para cupons promocionais.
     */
    public double calcularDescontoComPercentual(double valor, double percentual) {
        return valor * percentual;
    }
    
    /**
     * Calcula desconto por quantidade de itens.
     * Estratégia alternativa: quanto mais itens, maior o desconto.
     */
    public double calcularDescontoPorQuantidade(double valor, int quantidadeItens) {
        if (quantidadeItens >= 10) {
            return valor * 0.15; // 15% para 10+ itens
        } else if (quantidadeItens >= 5) {
            return valor * 0.10; // 10% para 5+ itens
        } else if (quantidadeItens >= 3) {
            return valor * 0.05; // 5% para 3+ itens
        }
        return 0.0;
    }
    
    /**
     * Calcula desconto por cliente fidelidade.
     * Exemplo de estratégia baseada em regra de negócio.
     */
    public double calcularDescontoFidelidade(double valor, boolean clienteFidelidade) {
        if (clienteFidelidade) {
            return valor * 0.15; // 15% para clientes fiéis
        }
        return 0.0;
    }
    
    /**
     * Desconto fixo em reais (não percentual).
     * Útil para promoções tipo "R$ 10 OFF".
     */
    public double calcularDescontoFixo(double valor, double valorDesconto) {
        return Math.min(valorDesconto, valor); // Não pode ser maior que o valor total
    }
}
