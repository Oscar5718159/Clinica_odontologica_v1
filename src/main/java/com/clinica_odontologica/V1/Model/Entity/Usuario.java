package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;



@Getter

@Setter
@Entity
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Long idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_persona", nullable = false)  
    private Persona persona;
    
    @Column(name="user_name",nullable=false,length = 100)
    private String nombreUsuario;

    @Column(name="password",nullable=false,length = 100)
    private String contraseña;      

    @Column(name="estado",nullable=false)
    private Boolean estado;
}
