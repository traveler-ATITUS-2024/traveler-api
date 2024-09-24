package com.traveler.api.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "despesa")
public class Despesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_despesa", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "categoria_id", referencedColumnName = "id_categoria")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "viagem_id", referencedColumnName = "id_viagem")
    private Viagem viagem;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "descricao", nullable = false)
    private String decricao;

    @Column(name = "data", nullable = false)
    private Timestamp data;

    @Column(name = "valor", nullable = false)
    private BigDecimal valor;

    public Despesa() {
    }

    public Despesa(Long id, Categoria categoria, Viagem viagem, String nome, String decricao, Timestamp data, BigDecimal valor) {
        this.id = id;
        this.categoria = categoria;
        this.viagem = viagem;
        this.nome = nome;
        this.decricao = decricao;
        this.data = data;
        this.valor = valor;
    }

    public Despesa(Categoria categoria, Viagem viagem, String nome, String decricao, Timestamp data, BigDecimal valor) {
        this.categoria = categoria;
        this.viagem = viagem;
        this.nome = nome;
        this.decricao = decricao;
        this.data = data;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Viagem getViagem() {
        return viagem;
    }

    public void setViagem(Viagem viagem) {
        this.viagem = viagem;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDecricao() {
        return decricao;
    }

    public void setDecricao(String decricao) {
        this.decricao = decricao;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }
}
