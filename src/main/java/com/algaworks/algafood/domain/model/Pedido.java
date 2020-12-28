package com.algaworks.algafood.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
@Entity
public class Pedido {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "subtotal")
    private BigDecimal subtotal;
    @Column(name = "taxa_frete")
    private BigDecimal taxaFrete;
    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    @Embedded
    private Endereco endereco;

    @Column(name = "status")
    private StatusPedido status;

    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "data_confirmacao")
    private LocalDateTime dataConfirmacao;
    @Column(name = "data_cancelamento")
    private LocalDateTime dataCancelamento;
    @Column(name = "data_entrega")
    private LocalDateTime dataEntrega;

    @ManyToOne
    @JoinColumn(nullable = false)
    private FormaPagamento formaPagamento;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Restaurante restaurante;

    @ManyToOne
    @JoinColumn(name = "usuario_cliente_id", nullable = false)
    private Usuario cliente;

    @OneToMany(mappedBy = "pedido")
    private List<ItemPedido> itens = new ArrayList<>();

}
