package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter

@Entity
@Table(name = "tratamientomedico")
public class TratamientoMedico {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratamientomedico")
    private Long idTratamientoMedico;

    @Column(name = "tratamiento_medico")
    private Boolean tratamientoMedico;

    @Column(name = "recibe_algun_medicamento")
    private Boolean recibeAlgunMedicamento;

    @Column(name = "tuvo_hemorragia_dental")
    private Boolean tuvoHemorragiaDental;

    @Column(name = "especifique", length = 50)
    private String especifique;

}