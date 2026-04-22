package com.clinica_odontologica.V1.Model.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Setter
@Getter
@Entity
@Table(name="rote")



public class Rote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rote")
    private Long idRote; 
    
    @Column(name="nombre_rote", length = 100)
    private String nombreRote;

}
