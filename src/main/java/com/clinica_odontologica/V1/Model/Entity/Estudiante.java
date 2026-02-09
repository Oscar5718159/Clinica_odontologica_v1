package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "usuario_estudiante")
public class Estudiante {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_estudiante")
    private Long idEstudiante;


    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)  
    private Usuario Usuario;
    

    @Column(name="gestion",nullable=false,length = 43)
    private String gestion;

    @Column(name="codigo_estudiante",nullable=false)
    private Integer codigoEstudiante;

    @Column(name="bloqueado",nullable=false)
    private Boolean bloqueado;
    
}