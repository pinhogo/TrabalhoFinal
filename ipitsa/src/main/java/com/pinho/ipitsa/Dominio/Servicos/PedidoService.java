package com.pinho.ipitsa.Dominio.Servicos;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pinho.ipitsa.Dominio.Dados.PedidoRepository;
import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;


public class PedidoService {
    private PedidoRepository pedidoRepository;

    @Autowired
    public PedidoService(PedidoRepository pedidoRepository){
        this.pedidoRepository = pedidoRepository;
    }

    Pedido salvar(Pedido pedido){
        return pedidoRepository.salvar(pedido);
    }

    Pedido buscarPorId(long id){
        return pedidoRepository.buscarPorId(id);
    }

    double valorTotal(long id){
        return pedidoRepository.valorTotal(id);
    }

    void excluir(long id)
    {
        pedidoRepository.excluir(id);
    }
    
    List<Pedido> buscarPorCliente(Cliente cliente){
        return pedidoRepository.buscarPorCliente(cliente);
    }

    List<Pedido> buscarPorStatus(Pedido.Status status){
        return pedidoRepository.buscarPorStatus(status);
    }

    List<ItemPedido> buscarItensDoPedido(long pedidoId){
        return pedidoRepository.buscarItensDoPedido(pedidoId);
    }

}
