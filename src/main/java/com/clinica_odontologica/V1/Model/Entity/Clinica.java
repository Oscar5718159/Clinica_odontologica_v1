package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "clinica")
public class Clinica {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_clinica")
    private Long idClinica;

    @Column(name = "nombre_clinica", length = 100, nullable = false)
    private String nombreClinica;

    @Column(name = "turno", length = 50)
    private String turno;

    @Column(name = "capacidad_maxima")
    private Integer capacidadMaxima;

    @OneToMany(mappedBy = "clinica")
    private List<Docente> docentes;

    // Constructores
    public Clinica() {}

    public Clinica(String nombreClinica, String turno, Integer capacidadMaxima) {
        this.nombreClinica = nombreClinica;
        this.turno = turno;
        this.capacidadMaxima = capacidadMaxima;
    }

    // Getters y Setters
    public Long getIdClinica() {
        return idClinica;
    }

    public void setIdClinica(Long idClinica) {
        this.idClinica = idClinica;
    }

    public String getNombreClinica() {
        return nombreClinica;
    }

    public void setNombreClinica(String nombreClinica) {
        this.nombreClinica = nombreClinica;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public Integer getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(Integer capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public List<Docente> getDocentes() {
        return docentes;
    }

    public void setDocentes(List<Docente> docentes) {
        this.docentes = docentes;
    }
}