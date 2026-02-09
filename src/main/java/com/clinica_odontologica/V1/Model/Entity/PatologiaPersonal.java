package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
@Table(name = "patologiapersonal")
public class PatologiaPersonal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patologiapersonal")
    private Long idPatologiaPersonal;

    @Column(name = "nombre_patologia", length = 255)
    private String nombrePatologia;

    @ManyToOne
    @JoinColumn(name = "id_consulta")
    private Consulta consulta;

    @Column(name = "alergias")
    private Boolean alergias;

    @Column(name = "embarazo")
    private Boolean embarazo;

    @Column(name = "semana_embarazo")
    private Integer semanaEmbarazo;

}