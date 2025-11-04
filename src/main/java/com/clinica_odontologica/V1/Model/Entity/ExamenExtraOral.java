package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;

@Entity
@Table(name = "examen_extra_oral")
public class ExamenExtraOral {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_examen_extra_oral")
    private Long idExamenExtraOral;

    @Column(length = 100)
    private String atm;

    @Column(name = "ganglios_linfaticos", length = 100)
    private String gangliosLinfaticos;

    @Column(length = 100)
    private String respirador;

    // Constructores
    public ExamenExtraOral() {
    }

    public ExamenExtraOral(String atm, String gangliosLinfaticos, String respirador) {
        this.atm = atm;
        this.gangliosLinfaticos = gangliosLinfaticos;
        this.respirador = respirador;
    }

    // Getters y Setters
    public Long getIdExamenExtraOral() {
        return idExamenExtraOral;
    }

    public void setIdExamenExtraOral(Long idExamenExtraOral) {
        this.idExamenExtraOral = idExamenExtraOral;
    }

    public String getAtm() {
        return atm;
    }

    public void setAtm(String atm) {
        this.atm = atm;
    }

    public String getGangliosLinfaticos() {
        return gangliosLinfaticos;
    }

    public void setGangliosLinfaticos(String gangliosLinfaticos) {
        this.gangliosLinfaticos = gangliosLinfaticos;
    }

    public String getRespirador() {
        return respirador;
    }

    public void setRespirador(String respirador) {
        this.respirador = respirador;
    }
}