package com.pinho.ipitsa.Dominio.Servicos;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinho.ipitsa.Dominio.Dados.PedidoRepository;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;

@Service
public class PedidoService {
    private PedidoRepository pedidoRepository;
    private ServicoEstoque servicoEstoque;
    private CalculadoraImpostos calculadoraImpostos;
    private CalculadoraDescontos calculadoraDescontos;

    @Autowired
    public PedidoService(
        PedidoRepository pedidoRepository,
        ServicoEstoque servicoEstoque,
        CalculadoraImpostos calculadoraImpostos,
        CalculadoraDescontos calculadoraDescontos
    ) {
        this.pedidoRepository = pedidoRepository;
        this.servicoEstoque = servicoEstoque;
        this.calculadoraImpostos = calculadoraImpostos;
        this.calculadoraDescontos = calculadoraDescontos;
    }

    /**
     * Submete um pedido para aprovação.
     * Valida estoque, calcula impostos e descontos.
     */
    public Pedido submeterPedido(Cliente cliente, List<ItemPedido> itens) {
        // 1. Verifica estoque (FAKE - sempre retorna true)
        if (!servicoEstoque.verificarEstoque(itens)) {
            throw new IllegalStateException("Estoque insuficiente");
        }
        
        // 2. Calcula o valor total do pedido
        double valorTotal = calcularValorTotal(itens);
        
        // 3. Calcula impostos usando a calculadora flexível
        double impostos = calculadoraImpostos.calcularImposto(valorTotal);
        
        // 4. Calcula descontos usando a calculadora flexível
        double desconto = calculadoraDescontos.calcularDesconto(valorTotal);
        
        // 5. Calcula valor final cobrado
        double valorCobrado = valorTotal + impostos - desconto;
        
        // 6. Reserva estoque (FAKE - sempre sucesso)
        servicoEstoque.reservarEstoque(itens, 0L); // ID será atualizado após salvar
        
        // 7. Cria o pedido com status APROVADO
        Pedido pedido = new Pedido(
            cliente,
            LocalDateTime.now(),
            itens,
            Pedido.Status.APROVADO,
            valorTotal,
            impostos,
            desconto,
            valorCobrado
        );

        // 8. Salva no banco
        Pedido pedidoSalvo = pedidoRepository.salvar(pedido);
        
        // 9. Atualiza reserva de estoque com ID correto
        servicoEstoque.reservarEstoque(itens, pedidoSalvo.getId());
        
        return pedidoSalvo;
    }

    /**
     * Cancela um pedido e libera o estoque reservado.
     */
    public Pedido cancelarPedido(Long id) {
        Pedido pedido = pedidoRepository.buscarPorId(id);
        
        if (pedido == null) {
            return null;
        }

        // Libera estoque reservado (FAKE - sempre sucesso)
        servicoEstoque.liberarEstoque(pedido.getItens(), id);

        // Cria nova instância com status CANCELADO (imutabilidade)
        Pedido pedidoCancelado = pedido.comStatus(Pedido.Status.CANCELADO);
        return pedidoRepository.salvar(pedidoCancelado);
    }

    /**
     * Atualiza o status de um pedido
     */
    public Pedido atualizarStatusPedido(Long id, Pedido.Status novoStatus) {
        Pedido pedido = pedidoRepository.buscarPorId(id);
        
        if (pedido == null) {
            return null;
        }

        // Cria nova instância com novo status (imutabilidade)
        Pedido pedidoAtualizado = pedido.comStatus(novoStatus);
        return pedidoRepository.salvar(pedidoAtualizado);
    }

    /**
     * Calcula o valor total dos itens do pedido
     */
    private double calcularValorTotal(List<ItemPedido> itens) {
        double total = 0.0;
        for (ItemPedido item : itens) {
            total += item.getItem().getPreco() * item.getQuantidade();
        }
        return total;
    }

    // Métodos auxiliares mantidos
    public Pedido salvar(Pedido pedido) {
        return pedidoRepository.salvar(pedido);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.buscarPorId(id);
    }

    public double valorTotal(Long id) {
        return pedidoRepository.valorTotal(id);
    }

    public void excluir(Long id) {
        pedidoRepository.excluir(id);
    }
    
    public List<Pedido> buscarPorCliente(Cliente cliente) {
        return pedidoRepository.buscarPorCliente(cliente);
    }

    public List<Pedido> buscarPorStatus(Pedido.Status status) {
        return pedidoRepository.buscarPorStatus(status);
    }

    public List<ItemPedido> buscarItensDoPedido(Long pedidoId) {
        return pedidoRepository.buscarItensDoPedido(pedidoId);
    }
}
