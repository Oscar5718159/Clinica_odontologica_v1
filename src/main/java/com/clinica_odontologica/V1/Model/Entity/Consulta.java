package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "consulta")
public class Consulta {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consulta")
    private Long idConsulta;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 500)
    private String observaciones;

    // Relaciones con otras entidades
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_paciente", nullable = false)
    private Paciente paciente;

    @ManyToOne
    @JoinColumn(name = "id_estudiante", nullable = false)
    private Estudiante estudiante;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_informante", nullable = false)
    private Informante informante;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_examen_extra_oral")
    private ExamenExtraOral examenExtraOral;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_examen_intra_oral")
    private ExamenIntraOral examenIntraOral;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_antecedentes_bucodentales")
    private AntecedentesBucodentales antecedentesBucodentales;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_antecedentes_higiene_oral")
    private AntecedentesHigieneOral antecedentesHigieneOral;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_patologia_personal")
    private PatologiaPersonal patologiaPersonal;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tratamiento_medico")
    private TratamientoMedico tratamientoMedico;

    // Constructores
    public Consulta() {
    }

    public Consulta(LocalDate fecha, String observaciones, Paciente paciente, Estudiante estudiante, Informante informante) {
        this.fecha = fecha;
        this.observaciones = observaciones;
        this.paciente = paciente;
        this.estudiante = estudiante;
        this.informante = informante;
    }

    // Getters y Setters
    public Long getIdConsulta() {
        return idConsulta;
    }

    public void setIdConsulta(Long idConsulta) {
        this.idConsulta = idConsulta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public Paciente getPaciente() {
        return paciente;
    }

    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Informante getInformante() {
        return informante;
    }

    public void setInformante(Informante informante) {
        this.informante = informante;
    }

    public ExamenExtraOral getExamenExtraOral() {
        return examenExtraOral;
    }

    public void setExamenExtraOral(ExamenExtraOral examenExtraOral) {
        this.examenExtraOral = examenExtraOral;
    }

    public ExamenIntraOral getExamenIntraOral() {
        return examenIntraOral;
    }

    public void setExamenIntraOral(ExamenIntraOral examenIntraOral) {
        this.examenIntraOral = examenIntraOral;
    }

    public AntecedentesBucodentales getAntecedentesBucodentales() {
        return antecedentesBucodentales;
    }

    public void setAntecedentesBucodentales(AntecedentesBucodentales antecedentesBucodentales) {
        this.antecedentesBucodentales = antecedentesBucodentales;
    }

    public AntecedentesHigieneOral getAntecedentesHigieneOral() {
        return antecedentesHigieneOral;
    }

    public void setAntecedentesHigieneOral(AntecedentesHigieneOral antecedentesHigieneOral) {
        this.antecedentesHigieneOral = antecedentesHigieneOral;
    }

    public PatologiaPersonal getPatologiaPersonal() {
        return patologiaPersonal;
    }

    public void setPatologiaPersonal(PatologiaPersonal patologiaPersonal) {
        this.patologiaPersonal = patologiaPersonal;
    }

    public TratamientoMedico getTratamientoMedico() {
        return tratamientoMedico;
    }

    public void setTratamientoMedico(TratamientoMedico tratamientoMedico) {
        this.tratamientoMedico = tratamientoMedico;
    }
}