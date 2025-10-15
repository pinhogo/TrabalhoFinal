package com.pinho.ipitsa.Dominio.Servicos;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;

/**
 * Serviço FAKE de estoque.
 * Sempre retorna que há estoque suficiente.
 */
@Service
public class ServicoEstoque {
    
    /**
     * Verifica se há estoque suficiente (FAKE - sempre retorna true).
     * 
     * @param itens Lista de itens do pedido
     * @return true sempre (estoque sempre suficiente)
     */
    public boolean verificarEstoque(List<ItemPedido> itens) {
        System.out.println("📦 [FAKE] Verificando estoque...");
        
        for (ItemPedido item : itens) {
            System.out.println("   - " + item.getItem().getDescricao() + 
                             " (quantidade: " + item.getQuantidade() + ")");
        }
        
        System.out.println("✅ [FAKE] Estoque SUFICIENTE para todos os itens!");
        return true; // Sempre tem estoque
    }
    
    /**
     * Verifica estoque de um ingrediente específico (FAKE).
     */
    public boolean verificarEstoqueIngrediente(Long ingredienteId, int quantidade) {
        System.out.println("📦 [FAKE] Verificando estoque do ingrediente " + ingredienteId);
        System.out.println("   Quantidade necessária: " + quantidade);
        System.out.println("✅ [FAKE] Estoque DISPONÍVEL!");
        return true;
    }
    
    /**
     * Reserva itens no estoque (FAKE).
     */
    public boolean reservarEstoque(List<ItemPedido> itens, Long pedidoId) {
        System.out.println("🔒 [FAKE] Reservando estoque para pedido " + pedidoId);
        
        for (ItemPedido item : itens) {
            System.out.println("   - Reservado: " + item.getItem().getDescricao() + 
                             " x" + item.getQuantidade());
        }
        
        System.out.println("✅ [FAKE] Estoque RESERVADO com sucesso!");
        return true;
    }
    
    /**
     * Libera reserva de estoque (FAKE).
     */
    public boolean liberarEstoque(List<ItemPedido> itens, Long pedidoId) {
        System.out.println("🔓 [FAKE] Liberando estoque do pedido " + pedidoId);
        
        for (ItemPedido item : itens) {
            System.out.println("   - Liberado: " + item.getItem().getDescricao() + 
                             " x" + item.getQuantidade());
        }
        
        System.out.println("✅ [FAKE] Estoque LIBERADO!");
        return true;
    }
    
    /**
     * Baixa definitiva no estoque (FAKE).
     */
    public boolean baixarEstoque(List<ItemPedido> itens, Long pedidoId) {
        System.out.println("📤 [FAKE] Baixando estoque para pedido " + pedidoId);
        
        for (ItemPedido item : itens) {
            System.out.println("   - Baixado: " + item.getItem().getDescricao() + 
                             " x" + item.getQuantidade());
        }
        
        System.out.println("✅ [FAKE] Baixa de estoque CONCLUÍDA!");
        return true;
    }
}
