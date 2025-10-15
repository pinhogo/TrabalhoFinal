package com.pinho.ipitsa.Dominio.Dados;

import java.util.List;

import com.pinho.ipitsa.Dominio.Entidades.Cliente;
import com.pinho.ipitsa.Dominio.Entidades.ItemPedido;
import com.pinho.ipitsa.Dominio.Entidades.Pedido;



public interface PedidoRepository {
    Pedido salvar(Pedido pedido);
    Pedido buscarPorId(long id);
    double valorTotal(long id);
    void excluir(long id);
    List<Pedido> buscarPorCliente(Cliente cliente);
    List<Pedido> buscarPorStatus(Pedido.Status status);
    List<ItemPedido> buscarItensDoPedido(long pedidoId);
}
