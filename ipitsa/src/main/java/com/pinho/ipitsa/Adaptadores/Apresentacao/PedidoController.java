package com.pinho.ipitsa.Adaptadores.Apresentacao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters.PedidoPresenter;
import com.pinho.ipitsa.Adaptadores.Apresentacao.Presenters.StatusPresenter;
import com.pinho.ipitsa.Aplicacao.CancelarPedidoUC;
import com.pinho.ipitsa.Aplicacao.ConsultarStatusPedidoUC;
import com.pinho.ipitsa.Aplicacao.ListarPedidosClienteUC;
import com.pinho.ipitsa.Aplicacao.ListarPedidosEntreguesUC;
import com.pinho.ipitsa.Aplicacao.PagarPedidoUC;
import com.pinho.ipitsa.Aplicacao.SubmeterPedidoUC;
import com.pinho.ipitsa.Aplicacao.Responses.PedidoResponse;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    
    private final SubmeterPedidoUC submeterPedidoUC;
    private final ConsultarStatusPedidoUC consultarStatusPedidoUC;
    private final CancelarPedidoUC cancelarPedidoUC;
    private final PagarPedidoUC pagarPedidoUC;
    private final ListarPedidosEntreguesUC listarPedidosEntreguesUC;
    private final ListarPedidosClienteUC listarPedidosClienteUC;

    public PedidoController(
        SubmeterPedidoUC submeterPedidoUC,
        ConsultarStatusPedidoUC consultarStatusPedidoUC,
        CancelarPedidoUC cancelarPedidoUC,
        PagarPedidoUC pagarPedidoUC,
        ListarPedidosEntreguesUC listarPedidosEntreguesUC,
        ListarPedidosClienteUC listarPedidosClienteUC
    ) {
        this.submeterPedidoUC = submeterPedidoUC;
        this.consultarStatusPedidoUC = consultarStatusPedidoUC;
        this.cancelarPedidoUC = cancelarPedidoUC;
        this.pagarPedidoUC = pagarPedidoUC;
        this.listarPedidosEntreguesUC = listarPedidosEntreguesUC;
        this.listarPedidosClienteUC = listarPedidosClienteUC;
    }

    @PostMapping
    @CrossOrigin("*")
    public PedidoPresenter submeterPedido(@RequestBody PedidoRequest pedidoRequest) {
        PedidoResponse pedidoResponse = submeterPedidoUC.run(
            pedidoRequest.getClienteCpf(),
            pedidoRequest.getItens()
        );
        return new PedidoPresenter(pedidoResponse);
    }

    @GetMapping("/{id}/status")
    @CrossOrigin("*")
    public StatusPresenter consultarStatus(@PathVariable Long id) {
        PedidoResponse pedidoResponse = consultarStatusPedidoUC.run(id);
        return new StatusPresenter(pedidoResponse);
    }

    @DeleteMapping("/{id}")
    @CrossOrigin("*")
    public PedidoPresenter cancelarPedido(@PathVariable Long id) {
        PedidoResponse pedidoResponse = cancelarPedidoUC.run(id);
        return new PedidoPresenter(pedidoResponse);
    }

    @PutMapping("/{id}/pagar")
    @CrossOrigin("*")
    public PedidoPresenter pagarPedido(@PathVariable Long id) {
        PedidoResponse pedidoResponse = pagarPedidoUC.run(id);
        return new PedidoPresenter(pedidoResponse);
    }

    @GetMapping("/entregues")
    @CrossOrigin("*")
    public List<PedidoPresenter> listarPedidosEntregues(
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim
    ) {
        List<PedidoResponse> pedidos = listarPedidosEntreguesUC.run(dataInicio, dataFim);
        return pedidos.stream()
            .map(PedidoPresenter::new)
            .toList();
    }

    @GetMapping("/cliente/{cpf}/entregues")
    @CrossOrigin("*")
    public List<PedidoPresenter> listarPedidosCliente(
        @PathVariable String cpf,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataInicio,
        @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dataFim
    ) {
        List<PedidoResponse> pedidos = listarPedidosClienteUC.run(cpf, dataInicio, dataFim);
        return pedidos.stream()
            .map(PedidoPresenter::new)
            .toList();
    }

    @GetMapping("/{id}")
    @CrossOrigin("*")
    public PedidoPresenter buscarPedido(@PathVariable Long id) {
        PedidoResponse pedidoResponse = consultarStatusPedidoUC.run(id);
        return new PedidoPresenter(pedidoResponse);
    }

    public static class PedidoRequest {
        private String clienteCpf;
        private List<ItemPedidoRequest> itens;

        public String getClienteCpf() {
            return clienteCpf;
        }

        public void setClienteCpf(String clienteCpf) {
            this.clienteCpf = clienteCpf;
        }

        public List<ItemPedidoRequest> getItens() {
            return itens;
        }

        public void setItens(List<ItemPedidoRequest> itens) {
            this.itens = itens;
        }
    }

    public static class ItemPedidoRequest {
        private Long produtoId;
        private int quantidade;

        public Long getProdutoId() {
            return produtoId;
        }

        public void setProdutoId(Long produtoId) {
            this.produtoId = produtoId;
        }

        public int getQuantidade() {
            return quantidade;
        }

        public void setQuantidade(int quantidade) {
            this.quantidade = quantidade;
        }
    }
}
