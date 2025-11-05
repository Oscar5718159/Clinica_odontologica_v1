package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.*;
import com.clinica_odontologica.V1.Model.Dto.ConsultaCompletaDTO;
import com.clinica_odontologica.V1.Model.Dao.*;
import com.clinica_odontologica.V1.Service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;
    
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public List<Consulta> obtenerTodos() {
        return consultaRepository.findAll();
    }

    @Override
    public Optional<Consulta> obtenerPorId(Long id) {
        return consultaRepository.findById(id);
    }

    @Override
    public Consulta guardar(Consulta consulta) {
        return consultaRepository.save(consulta);
    }

    @Override
    @Transactional
    public Consulta guardarConsultaCompleta(ConsultaCompletaDTO consultaDTO) {
        
        // 1. Obtener Paciente y Estudiante (deben existir previamente)
        Paciente paciente = pacienteRepository.findById(consultaDTO.getIdPaciente())
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado"));
            
        Estudiante estudiante = estudianteRepository.findById(consultaDTO.getIdEstudiante())
            .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        
        // 2. Crear y guardar Informante
        Informante informante = new Informante();
        informante.setNombres(consultaDTO.getInformanteNombres());
        informante.setApellidoPaterno(consultaDTO.getInformanteApellidoPaterno());
        informante.setApellidoMaterno(consultaDTO.getInformanteApellidoMaterno());
        informante.setDireccion(consultaDTO.getInformanteDireccion());
        informante.setTelefono(consultaDTO.getInformanteTelefono());
        
        // 3. Crear y guardar PatologiaPersonal
        PatologiaPersonal patologiaPersonal = new PatologiaPersonal();
        patologiaPersonal.setNombrePatologia(consultaDTO.getNombrePatologia());
        patologiaPersonal.setAlergias(consultaDTO.getAlergias());
        patologiaPersonal.setEmbarazo(consultaDTO.getEmbarazo());
        patologiaPersonal.setSemanaEmbarazo(consultaDTO.getSemanaEmbarazo());

        // 4. Crear y guardar TratamientoMedico
        TratamientoMedico tratamientoMedico = new TratamientoMedico();
        tratamientoMedico.setTratamientoMedico(consultaDTO.getTratamientoMedico());
        tratamientoMedico.setRecibeAlgunMedicamento(consultaDTO.getRecibeAlgunMedicamento());
        tratamientoMedico.setTuvoHemorragiaDental(consultaDTO.getTuvoHemorragiaDental());
        tratamientoMedico.setEspecifique(consultaDTO.getEspecifiqueHemorragia());
        
        // 5. Crear y guardar ExamenExtraOral
        ExamenExtraOral examenExtraOral = new ExamenExtraOral();
        examenExtraOral.setAtm(consultaDTO.getAtm());
        examenExtraOral.setGangliosLinfaticos(consultaDTO.getGangliosLinfaticos());
        examenExtraOral.setRespirador(consultaDTO.getRespirador());
        
        // 6. Crear y guardar ExamenIntraOral
        ExamenIntraOral examenIntraOral = new ExamenIntraOral();
        examenIntraOral.setLabios(consultaDTO.getLabios());
        examenIntraOral.setLengua(consultaDTO.getLengua());
        examenIntraOral.setPaladar(consultaDTO.getPaladar());
        examenIntraOral.setPisoDeLaBoca(consultaDTO.getPisoDeLaBoca());
        examenIntraOral.setMucosaYugal(consultaDTO.getMucosaYugal());
        examenIntraOral.setEncias(consultaDTO.getEncias());
        examenIntraOral.setUtilizaProtesisDental(consultaDTO.getUtilizaProtesisDental());
        
        // 7. Crear y guardar AntecedentesBucodentales
        AntecedentesBucodentales antecedentesBucodentales = new AntecedentesBucodentales();
        antecedentesBucodentales.setFechaRevision(consultaDTO.getFechaRevision());
        
        // Construir hábitos como string
        StringBuilder habitosBuilder = new StringBuilder();
        if (Boolean.TRUE.equals(consultaDTO.getHabitoFuma())) habitosBuilder.append("Fuma, ");
        if (Boolean.TRUE.equals(consultaDTO.getHabitoBebe())) habitosBuilder.append("Bebe, ");
        if (consultaDTO.getOtrosHabitos() != null && !consultaDTO.getOtrosHabitos().isEmpty()) {
            habitosBuilder.append(consultaDTO.getOtrosHabitos());
        }
        String habitos = habitosBuilder.toString();
        if (habitos.endsWith(", ")) {
            habitos = habitos.substring(0, habitos.length() - 2);
        }
        antecedentesBucodentales.setHabitos(habitos);
        
        // 8. Crear y guardar AntecedentesHigieneOral
        AntecedentesHigieneOral antecedentesHigieneOral = new AntecedentesHigieneOral();
        antecedentesHigieneOral.setUtilizaCepilloDental(consultaDTO.getUtilizaCepilloDental());
        antecedentesHigieneOral.setUtilizaHiloDental(consultaDTO.getUtilizaHiloDental());
        antecedentesHigieneOral.setUtilizaEnjuagueBucal(consultaDTO.getUtilizaEnjuagueBucal());
        antecedentesHigieneOral.setFrecuenciaCepillo(consultaDTO.getFrecuenciaCepillo());
        
        // Construir duranteElCepillado
        String duranteElCepillado = Boolean.TRUE.equals(consultaDTO.getSangradoEncias()) ? "Sangrado de encías" : "Sin sangrado";
        antecedentesHigieneOral.setDuranteElCepillado(duranteElCepillado);
        
        antecedentesHigieneOral.setHigieneBucal(consultaDTO.getHigieneBucal());
        
        // 9. Crear y guardar la Consulta principal
        Consulta consulta = new Consulta();
        consulta.setFecha(consultaDTO.getFecha());
        consulta.setObservaciones(consultaDTO.getObservaciones());
        consulta.setPaciente(paciente);
        consulta.setEstudiante(estudiante);
        consulta.setInformante(informante);
        consulta.setPatologiaPersonal(patologiaPersonal);
        consulta.setTratamientoMedico(tratamientoMedico);
        consulta.setExamenExtraOral(examenExtraOral);
        consulta.setExamenIntraOral(examenIntraOral);
        consulta.setAntecedentesBucodentales(antecedentesBucodentales);
        consulta.setAntecedentesHigieneOral(antecedentesHigieneOral);
        
        return consultaRepository.save(consulta);
    }

    @Override
    public void eliminar(Long id) {
        consultaRepository.deleteById(id);
    }

    @Override
    public List<Consulta> obtenerPorFecha(LocalDate fecha) {
        return consultaRepository.findByFecha(fecha);
    }

    @Override
    public List<Consulta> obtenerPorPaciente(Long idPaciente) {
        return consultaRepository.findByPacienteIdPaciente(idPaciente);
    }

    @Override
    public List<Consulta> obtenerPorEstudiante(Long idEstudiante) {
        return consultaRepository.findByEstudianteIdEstudiante(idEstudiante);
    }

    @Override
    public List<Consulta> obtenerPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return consultaRepository.findByFechaBetween(fechaInicio, fechaFin);
    }
}