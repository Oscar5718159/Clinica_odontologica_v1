package com.clinica_odontologica.V1.Model.Entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Getter@Setter
@Entity
@Table(name="tratamiento_realizado")

public class TratamientoRealizado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_tratamiento_realizado")
    private Long idTratamientoRealizado;

    @ManyToOne
    @JoinColumn(name="id_inscripcion_materia")
    private InscripcionMateria inscripcionMateria;

    @ManyToOne
    @JoinColumn(name="id_detalle_consentimiento")
    private DetalleConsentimiento detalleConsentimiento;
    @ManyToOne
    @JoinColumn(name="id_tratamiento")
    private Tratamiento tratamiento;

    @ManyToOne
    @JoinColumn(name="id_docente")
    private Docente docente;

    @Column(name="cantidad_tratamiento")
    private Integer cantidadTratamiento;
    @Column(name="fecha_realizacion")
    private LocalDate fechaRealizacion;
    @Column(name="validacion")
    private Boolean validacion;
    @Column(name="fecha_validacion")
    private LocalDate fechaValidacion;    
}
