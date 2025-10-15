package com.pinho.ipitsa.Aplicacao;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinho.ipitsa.Adaptadores.Apresentacao.PedidoController.ItemPedidoRequest;
import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;
import com.pinho.ipitsa.Dominio.Dados.ClienteRepository;
import com.pinho.ipitsa.Dominio.Dados.ProdutosRepository;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;
import com.pinho.ipitsa.Dominio.Entidades.Produto;
import com.pinho.ipitsa.Dominio.Servicos.PedidoService;

@Component
public class SubmeterPedidoUC {
    private PedidoService pedidoService;
    private ClienteRepository clienteRepository;
    private ProdutosRepository produtosRepository;

    @Autowired
    public SubmeterPedidoUC(
        PedidoService pedidoService,
        ClienteRepository clienteRepository,
        ProdutosRepository produtosRepository
    ) {
        this.pedidoService = pedidoService;
        this.clienteRepository = clienteRepository;
        this.produtosRepository = produtosRepository;
    }

    public PedidoResponse run(String clienteCpf, List<ItemPedidoRequest> itensRequest) {
        // Buscar cliente
        Cliente cliente = clienteRepository.buscarPorCpf(clienteCpf);
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não encontrado: " + clienteCpf);
        }

        // Construir lista de ItemPedido
        List<ItemPedido> itens = itensRequest.stream()
            .map(itemRequest -> {
                Produto produto = produtosRepository.recuperaProdutoPorid(itemRequest.getProdutoId());
                if (produto == null) {
                    throw new IllegalArgumentException("Produto não encontrado: " + itemRequest.getProdutoId());
                }
                return new ItemPedido(produto, itemRequest.getQuantidade());
            })
            .collect(Collectors.toList());

        Pedido pedido = pedidoService.submeterPedido(cliente, itens);
        return new PedidoResponse(pedido);
    }
}
