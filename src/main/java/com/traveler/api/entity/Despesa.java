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

    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "viagem_id")
    private Long viagemId;

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

    public Despesa(Long id, Long categoriaId, Long viagemId, String nome, String decricao, Timestamp data, BigDecimal valor) {
        this.id = id;
        this.categoriaId = categoriaId;
        this.viagemId = viagemId;
        this.nome = nome;
        this.decricao = decricao;
        this.data = data;
        this.valor = valor;
    }

    public Despesa(Long categoriaId, Long viagemId, String nome, String decricao, Timestamp data, BigDecimal valor) {
        this.categoriaId = categoriaId;
        this.viagemId = viagemId;
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

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public Long getViagemId() {
        return viagemId;
    }

    public void setViagemId(Long viagemId) {
        this.viagemId = viagemId;
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
