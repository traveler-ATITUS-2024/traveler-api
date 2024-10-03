package com.traveler.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "viagem")
public class Viagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_viagem")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", referencedColumnName = "id_usuario")
    private Usuario usuario;

    @Column(name = "status_id")
    private Long statusId;

    @Column(name = "nome")
    private String nome;

    @Column(name = "data_ida")
    private Timestamp dataIda;

    @Column(name = "data_volta")
    private Timestamp dataVolta;

    @Column(name = "valor_prv")
    private BigDecimal valorPrv;

    @Column(name = "valor_real")
    private BigDecimal valorReal = BigDecimal.ZERO;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Transient
    @JsonIgnore
    private double valorTotalDespesas;

    public double getValorTotalDespesas() {
        return valorTotalDespesas;
    }

    public void setValorTotalDespesas(double valorTotalDespesas) {
        this.valorTotalDespesas = valorTotalDespesas;
    }

    public Viagem() {
    }

    public Viagem(Long id, Usuario usuario, String nome, Long statusId, Timestamp dataIda, Timestamp dataVolta, BigDecimal valorPrv, BigDecimal valorReal, String latitude, String longitude) {
        this.id = id;
        this.usuario = usuario;
        this.statusId = statusId;
        this.nome = nome;
        this.dataIda = dataIda;
        this.dataVolta = dataVolta;
        this.valorPrv = valorPrv;
        this.valorReal = valorReal;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Viagem(String nome, Long statusId, Timestamp dataIda, Timestamp dataVolta, BigDecimal valorPrv, BigDecimal valorReal, String latitude, String longitude) {
        this.statusId = statusId;
        this.nome = nome;
        this.dataIda = dataIda;
        this.dataVolta = dataVolta;
        this.valorPrv = valorPrv;
        this.valorReal = valorReal;
        this.latitude = latitude;
        this.longitude = longitude;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public Timestamp getDataIda() {
        return dataIda;
    }

    public void setDataIda(Timestamp dataIda) {
        this.dataIda = dataIda;
    }

    public Timestamp getDataVolta() {
        return dataVolta;
    }

    public void setDataVolta(Timestamp dataVolta) {
        this.dataVolta = dataVolta;
    }

    public BigDecimal getValorPrv() {
        return valorPrv;
    }

    public void setValorPrv(BigDecimal valorPrv) {
        this.valorPrv = valorPrv;
    }

    public BigDecimal getValorReal() {
        return valorReal;
    }

    public void setValorReal(BigDecimal valorReal) {
        this.valorReal = valorReal;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
