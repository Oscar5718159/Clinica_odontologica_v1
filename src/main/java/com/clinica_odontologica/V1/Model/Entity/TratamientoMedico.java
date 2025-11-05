package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;

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

    // Constructores
    public TratamientoMedico() {
    }

    public TratamientoMedico(Boolean tratamientoMedico, Boolean recibeAlgunMedicamento, Boolean tuvoHemorragiaDental, String especifique) {
        this.tratamientoMedico = tratamientoMedico;
        this.recibeAlgunMedicamento = recibeAlgunMedicamento;
        this.tuvoHemorragiaDental = tuvoHemorragiaDental;
        this.especifique = especifique;
    }

    // Getters y Setters
    public Long getIdTratamientoMedico() {
        return idTratamientoMedico;
    }

    public void setIdTratamientoMedico(Long idTratamientoMedico) {
        this.idTratamientoMedico = idTratamientoMedico;
    }

    public Boolean getTratamientoMedico() {
        return tratamientoMedico;
    }

    public void setTratamientoMedico(Boolean tratamientoMedico) {
        this.tratamientoMedico = tratamientoMedico;
    }

    public Boolean getRecibeAlgunMedicamento() {
        return recibeAlgunMedicamento;
    }

    public void setRecibeAlgunMedicamento(Boolean recibeAlgunMedicamento) {
        this.recibeAlgunMedicamento = recibeAlgunMedicamento;
    }

    public Boolean getTuvoHemorragiaDental() {
        return tuvoHemorragiaDental;
    }

    public void setTuvoHemorragiaDental(Boolean tuvoHemorragiaDental) {
        this.tuvoHemorragiaDental = tuvoHemorragiaDental;
    }

    public String getEspecifique() {
        return especifique;
    }

    public void setEspecifique(String especifique) {
        this.especifique = especifique;
    }
}