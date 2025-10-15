package com.pinho.ipitsa.Dominio.Servicos;

import org.springframework.stereotype.Service;

/**
 * Serviço FAKE de pagamentos.
 * Sempre retorna sucesso para simulação.
 */
@Service
public class ServicoPagamento {
    
    /**
     * Processa pagamento (FAKE - sempre retorna sucesso).
     * 
     * @param pedidoId ID do pedido
     * @param valor Valor a ser pago
     * @param metodoPagamento Método de pagamento (cartão, pix, dinheiro)
     * @return true sempre (pagamento sempre aprovado)
     */
    public boolean processarPagamento(Long pedidoId, double valor, String metodoPagamento) {
        // Simula processamento
        System.out.println("💳 [FAKE] Processando pagamento...");
        System.out.println("   Pedido: " + pedidoId);
        System.out.println("   Valor: R$ " + (valor / 100.0));
        System.out.println("   Método: " + metodoPagamento);
        
        // Simula delay de processamento
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        System.out.println("✅ [FAKE] Pagamento APROVADO!");
        return true; // Sempre aprovado
    }
    
    /**
     * Verifica status de pagamento (FAKE).
     */
    public String verificarStatusPagamento(Long pedidoId) {
        return "APROVADO";
    }
    
    /**
     * Estorna pagamento (FAKE).
     */
    public boolean estornarPagamento(Long pedidoId, double valor) {
        System.out.println("🔄 [FAKE] Estornando pagamento do pedido " + pedidoId);
        System.out.println("   Valor: R$ " + (valor / 100.0));
        System.out.println("✅ [FAKE] Estorno APROVADO!");
        return true;
    }
}
