package com.clinica_odontologica.V1.Model.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;

import lombok.Getter;   
import lombok.Setter;
import java.util.List;

@Getter
@Setter


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
    @JoinColumn(name = "id_persona", nullable = false)
    private Informante informante;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_examen_extra_oral")
    private ExamenExtraOral examenExtraOral;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_examen_intra_oral")
    private ExamenIntraOral examenIntraOral;
    
    // Relación OneToMany con PatologiaPersonal
    @OneToMany(mappedBy = "consulta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PatologiaPersonal> patologiasPersonales = new ArrayList<>();

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_antecedentes_bucodentales")
    private AntecedentesBucodentales antecedentesBucodentales;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_tratamiento_medico")
    private TratamientoMedico tratamientoMedico;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_antecedentes_higiene_oral")
    private AntecedentesHigieneOral antecedentesHigieneOral;


}