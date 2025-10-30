package com.clinica_odontologica.V1.Model.Entity;
import jakarta.persistence.*;
@Entity

@Table(name = "persona")
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_persona;

    @Column(nullable = false, length = 50)
    private String nombre;


    @Column(nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(nullable = false, length = 50)
    private String apellidoMaterno;


    @Column(nullable = false)
    private Integer edad;
    
    @Column(nullable = false, length = 1)
    private Character sexo; // 'M' o 'F'

    // Constructor, getters y setters
    public Persona() {
    }

    public Long getId_persona() {
        return id_persona;
    }

    public void setId_persona(Long id_persona) {
        this.id_persona = id_persona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }



    public String getApellidoPaterno() {
        return apellidoPaterno;
    }
    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }
    public String getApellidoMaterno() {
        return apellidoMaterno;
    }
    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }



    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Character getSexo() {
        return sexo;
    }

    public void setSexo(Character sexo) {
        if (sexo != null && (sexo == 'M' || sexo == 'F')) {
            this.sexo = sexo;
        } else {
            throw new IllegalArgumentException("El sexo debe ser 'M' o 'F'");
        }
    }
}