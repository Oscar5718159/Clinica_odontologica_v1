package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "antecedentes_bucodentales")
public class AntecedentesBucodentales {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_antecedentes_bucodentales")
    private Long idAntecedentesBucodentales;

    @Column(name = "fecha_revision")
    private LocalDate fechaRevision;

    @Column(length = 100)
    private String habitos;

    // Constructores
    public AntecedentesBucodentales() {
    }

    public AntecedentesBucodentales(LocalDate fechaRevision, String habitos) {
        this.fechaRevision = fechaRevision;
        this.habitos = habitos;
    }

    // Getters y Setters
    public Long getIdAntecedentesBucodentales() {
        return idAntecedentesBucodentales;
    }

    public void setIdAntecedentesBucodentales(Long idAntecedentesBucodentales) {
        this.idAntecedentesBucodentales = idAntecedentesBucodentales;
    }

    public LocalDate getFechaRevision() {
        return fechaRevision;
    }

    public void setFechaRevision(LocalDate fechaRevision) {
        this.fechaRevision = fechaRevision;
    }

    public String getHabitos() {
        return habitos;
    }

    public void setHabitos(String habitos) {
        this.habitos = habitos;
    }
}