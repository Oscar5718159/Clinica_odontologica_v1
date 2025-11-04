package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "antecedentes_higiene_oral")
public class AntecedentesHigieneOral {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_antecedentes_higiene_oral")
    private Long idAntecedentesHigieneOral;

    @Column(name = "utiliza_cepillo_dental")
    private Boolean utilizaCepilloDental;

    @Column(name = "utiliza_hilo_dental")
    private Boolean utilizaHiloDental;

    @Column(name = "utiliza_enjuague_bucal")
    private Boolean utilizaEnjuagueBucal;

    @Column(name = "frecuencia_cepillo", length = 50)
    private String frecuenciaCepillo;

    @Column(name = "durante_el_cepillado", length = 100)
    private String duranteElCepillado;

    @Column(name = "higiene_bucal", length = 20)
    private String higieneBucal;

    // Constructores
    public AntecedentesHigieneOral() {
    }

    public AntecedentesHigieneOral(Boolean utilizaCepilloDental, Boolean utilizaHiloDental, Boolean utilizaEnjuagueBucal, String frecuenciaCepillo, String duranteElCepillado, String higieneBucal) {
        this.utilizaCepilloDental = utilizaCepilloDental;
        this.utilizaHiloDental = utilizaHiloDental;
        this.utilizaEnjuagueBucal = utilizaEnjuagueBucal;
        this.frecuenciaCepillo = frecuenciaCepillo;
        this.duranteElCepillado = duranteElCepillado;
        this.higieneBucal = higieneBucal;
    }

    // Getters y Setters
    public Long getIdAntecedentesHigieneOral() {
        return idAntecedentesHigieneOral;
    }

    public void setIdAntecedentesHigieneOral(Long idAntecedentesHigieneOral) {
        this.idAntecedentesHigieneOral = idAntecedentesHigieneOral;
    }

    public Boolean getUtilizaCepilloDental() {
        return utilizaCepilloDental;
    }

    public void setUtilizaCepilloDental(Boolean utilizaCepilloDental) {
        this.utilizaCepilloDental = utilizaCepilloDental;
    }

    public Boolean getUtilizaHiloDental() {
        return utilizaHiloDental;
    }

    public void setUtilizaHiloDental(Boolean utilizaHiloDental) {
        this.utilizaHiloDental = utilizaHiloDental;
    }

    public Boolean getUtilizaEnjuagueBucal() {
        return utilizaEnjuagueBucal;
    }

    public void setUtilizaEnjuagueBucal(Boolean utilizaEnjuagueBucal) {
        this.utilizaEnjuagueBucal = utilizaEnjuagueBucal;
    }

    public String getFrecuenciaCepillo() {
        return frecuenciaCepillo;
    }

    public void setFrecuenciaCepillo(String frecuenciaCepillo) {
        this.frecuenciaCepillo = frecuenciaCepillo;
    }

    public String getDuranteElCepillado() {
        return duranteElCepillado;
    }

    public void setDuranteElCepillado(String duranteElCepillado) {
        this.duranteElCepillado = duranteElCepillado;
    }

    public String getHigieneBucal() {
        return higieneBucal;
    }

    public void setHigieneBucal(String higieneBucal) {
        this.higieneBucal = higieneBucal;
    }
}