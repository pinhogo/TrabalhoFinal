package com.pinho.ipitsa.Dominio.Servicos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinho.ipitsa.Dominio.Dados.PedidoRepository;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;

/**
 * Serviço FAKE de entregas.
 * Simula o processo de entrega e atualiza o status do pedido no banco.
 */
@Service
public class ServicoEntregas {
    
    private final PedidoRepository pedidoRepository;
    
    @Autowired
    public ServicoEntregas(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }
    
    /**
     * Simula o início da entrega.
     * Atualiza status do pedido para EM_ENTREGA.
     */
    public Pedido iniciarEntrega(Long pedidoId) {
        System.out.println("🚚 [FAKE] Iniciando entrega do pedido " + pedidoId);
        
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado: " + pedidoId);
        }
        
        // Simula processo de separação para entrega
        try {
            System.out.println("   📋 Separando pedido...");
            Thread.sleep(500);
            System.out.println("   📦 Embalando pedido...");
            Thread.sleep(500);
            System.out.println("   🛵 Entregador a caminho...");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Atualiza status no banco
        Pedido pedidoAtualizado = pedido.comStatus(Pedido.Status.TRANSPORTE);
        pedidoRepository.salvar(pedidoAtualizado);
        
        System.out.println("✅ [FAKE] Pedido SAIU PARA ENTREGA!");
        return pedidoAtualizado;
    }
    
    /**
     * Simula a conclusão da entrega.
     * Atualiza status do pedido para ENTREGUE.
     */
    public Pedido concluirEntrega(Long pedidoId) {
        System.out.println("🏠 [FAKE] Finalizando entrega do pedido " + pedidoId);
        
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado: " + pedidoId);
        }
        
        // Simula tempo de entrega
        try {
            System.out.println("   🛵 Entregador no caminho...");
            Thread.sleep(1000);
            System.out.println("   🔔 Entregador chegou no endereço...");
            Thread.sleep(500);
            System.out.println("   📦 Cliente recebeu o pedido...");
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        // Atualiza status no banco
        Pedido pedidoAtualizado = pedido.comStatus(Pedido.Status.ENTREGUE);
        pedidoRepository.salvar(pedidoAtualizado);
        
        System.out.println("✅ [FAKE] Pedido ENTREGUE com sucesso!");
        return pedidoAtualizado;
    }
    
    /**
     * Simula todo o processo de entrega (início + conclusão).
     */
    public Pedido simularEntregaCompleta(Long pedidoId) {
        System.out.println("🚀 [FAKE] Simulando entrega completa do pedido " + pedidoId);
        iniciarEntrega(pedidoId);
        return concluirEntrega(pedidoId);
    }
    
    /**
     * Verifica status da entrega.
     */
    public String verificarStatusEntrega(Long pedidoId) {
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            return "PEDIDO_NAO_ENCONTRADO";
        }
        
        Pedido.Status status = pedido.getStatus();
        System.out.println("📍 [FAKE] Status da entrega do pedido " + pedidoId + ": " + status);
        
        return switch (status) {
            case TRANSPORTE -> "A CAMINHO - Entregador a 10 minutos de distância";
            case ENTREGUE -> "ENTREGUE - Pedido foi entregue com sucesso";
            case PREPARACAO, PRONTO -> "AGUARDANDO - Pedido ainda está sendo preparado";
            default -> "PROCESSANDO - Aguarde...";
        };
    }
    
    /**
     * Cancela entrega e retorna pedido ao status anterior.
     */
    public Pedido cancelarEntrega(Long pedidoId) {
        System.out.println("❌ [FAKE] Cancelando entrega do pedido " + pedidoId);
        
        Pedido pedido = pedidoRepository.buscarPorId(pedidoId);
        if (pedido == null) {
            throw new IllegalArgumentException("Pedido não encontrado: " + pedidoId);
        }
        
        // Retorna para status PRONTO
        Pedido pedidoAtualizado = pedido.comStatus(Pedido.Status.PRONTO);
        pedidoRepository.salvar(pedidoAtualizado);
        
        System.out.println("✅ [FAKE] Entrega CANCELADA - Pedido retornou ao status PRONTO");
        return pedidoAtualizado;
    }
}
