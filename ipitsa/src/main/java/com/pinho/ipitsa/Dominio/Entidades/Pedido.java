package com.pinho.ipitsa.Dominio.Entidades;

import java.time.LocalDateTime;
import java.util.List;

public class Pedido {
    public enum Status {
        NOVO,
        APROVADO,
        PAGO,
        AGUARDANDO,
        PREPARACAO,
        PRONTO,
        TRANSPORTE,
        ENTREGUE
    }
    private long id;
    private Cliente cliente;
    private LocalDateTime dataHoraPagamento;
    private List<ItemPedido> itens;
    private Status status;
    private double valor;
    private double impostos;
    private double desconto;
    private double valorCobrado;

    // Construtor completo (original)
    public Pedido(long id, Cliente cliente, LocalDateTime dataHoraPagamento, List<ItemPedido> itens,
            Pedido.Status status, double valor, double impostos, double desconto, double valorCobrado) {
        this.id = id;
        this.cliente = cliente;
        this.dataHoraPagamento = dataHoraPagamento;
        this.itens = itens;
        this.status = status;
        this.valor = valor;
        this.impostos = impostos;
        this.desconto = desconto;
        this.valorCobrado = valorCobrado;
    }

    // Construtor para criar novo pedido (sem ID, será gerado pelo banco)
    public Pedido(Cliente cliente, LocalDateTime dataHoraPagamento, List<ItemPedido> itens,
            Pedido.Status status, double valor, double impostos, double desconto, double valorCobrado) {
        this(0, cliente, dataHoraPagamento, itens, status, valor, impostos, desconto, valorCobrado);
    }

    // Método para criar uma cópia com novo ID (usado após salvar no banco)
    public Pedido comId(long novoId) {
        return new Pedido(novoId, this.cliente, this.dataHoraPagamento, this.itens,
                this.status, this.valor, this.impostos, this.desconto, this.valorCobrado);
    }

    // Método para criar uma cópia com novo status (transições de estado)
    public Pedido comStatus(Status novoStatus) {
        return new Pedido(this.id, this.cliente, this.dataHoraPagamento, this.itens,
                novoStatus, this.valor, this.impostos, this.desconto, this.valorCobrado);
    }

    // Método para adicionar itens
    public Pedido comItens(List<ItemPedido> novosItens) {
        return new Pedido(this.id, this.cliente, this.dataHoraPagamento, novosItens,
                this.status, this.valor, this.impostos, this.desconto, this.valorCobrado);
    }

    public long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public LocalDateTime getDataHoraPagamento() {
        return dataHoraPagamento;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status){
        this.status = status;
    }

    public double getValor() {
        return valor;
    }

    public double getImpostos() {
        return impostos;
    }

    public double getDesconto() {
        return desconto;
    }

    public double getValorCobrado() {
        return valorCobrado;
    }
}
