package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.*;
import com.clinica_odontologica.V1.Model.Dto.ConsultaCompletaDTO;
import com.clinica_odontologica.V1.Model.Dto.PatologiaPersonalDTO;
import com.clinica_odontologica.V1.Model.Dao.*;
import com.clinica_odontologica.V1.Service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
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
        patologiaPersonal.setAnemia(consultaDTO.getAnemia());
        patologiaPersonal.setCardiopatias(consultaDTO.getCardiopatias());
        patologiaPersonal.setEnfGastricos(consultaDTO.getEnfGastricos());
        patologiaPersonal.setHepatitis(consultaDTO.getHepatitis());
        patologiaPersonal.setTuberculosis(consultaDTO.getTuberculosis());
        patologiaPersonal.setAsma(consultaDTO.getAsma());
        patologiaPersonal.setDiabetesMel(consultaDTO.getDiabetesMel());
        patologiaPersonal.setEpilepsia(consultaDTO.getEpilepsia());
        patologiaPersonal.setHipertension(consultaDTO.getHipertension());
        patologiaPersonal.setOtros(consultaDTO.getOtros());
        patologiaPersonal.setNinguno(consultaDTO.getNinguno());
        patologiaPersonal.setAlergias(consultaDTO.getAlergias());
        patologiaPersonal.setEmbarazo(consultaDTO.getEmbarazo());
        patologiaPersonal.setSemanaEmbarazo(consultaDTO.getSemanaEmbarazo());




        // 4. Crear y guardar TratamientoMedico
        TratamientoMedico tratamientoMedico = new TratamientoMedico();
        tratamientoMedico.setTratamientoMedico(consultaDTO.getTratamientoMedico());
        tratamientoMedico.setRecibeAlgunMedicamento(consultaDTO.getRecibeAlgunMedicamento());
        tratamientoMedico.setTuvoHemorragiaDental(consultaDTO.getTuvoHemorragiaDental());
        tratamientoMedico.setEspecifiqueHemorragia(consultaDTO.getEspecifiqueHemorragia());
        
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
        antecedentesBucodentales.setFuma(consultaDTO.getHabitoFuma());
        antecedentesBucodentales.setBebe(consultaDTO.getHabitoBebe());
        antecedentesBucodentales.setOtrosHabitos(consultaDTO.getOtrosHabitos());

        
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

    @Override
    public List<Consulta> buscarPorCriterio(String criterio) {
        return consultaRepository.buscarPorCriterio(criterio);
    }

    @Override
    public Optional<Consulta> obtenerConsultaCompleta(Long idConsulta) {
        return consultaRepository.findById(idConsulta);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ConsultaCompletaDTO> obtenerConsultasCompletasPorPaciente(Long idPaciente) {
        try {
            System.out.println("🔍 Buscando consultas completas para paciente: " + idPaciente);
            
            // Obtener las consultas con todas las relaciones usando el nuevo método del repository
            List<Consulta> consultas = consultaRepository.findByPacienteIdPacienteWithRelations(idPaciente);
            System.out.println("📊 Consultas encontradas: " + consultas.size());
            
            // Mapear a DTO manualmente
            List<ConsultaCompletaDTO> dtos = new ArrayList<>();
            
            for (Consulta consulta : consultas) {
                ConsultaCompletaDTO dto = new ConsultaCompletaDTO();
                
                System.out.println("🔄 Mapeando consulta ID: " + consulta.getIdConsulta());
                
                // Datos básicos de la consulta
                dto.setFecha(consulta.getFecha());
                dto.setObservaciones(consulta.getObservaciones());
                dto.setIdPaciente(consulta.getPaciente().getIdPaciente());
                dto.setIdEstudiante(consulta.getEstudiante().getIdEstudiante());
                
                // Informante
                if (consulta.getInformante() != null) {
                    dto.setInformanteNombres(consulta.getInformante().getNombres());
                    dto.setInformanteApellidoPaterno(consulta.getInformante().getApellidoPaterno());
                    dto.setInformanteApellidoMaterno(consulta.getInformante().getApellidoMaterno());
                    dto.setInformanteDireccion(consulta.getInformante().getDireccion());
                    dto.setInformanteTelefono(consulta.getInformante().getTelefono());
                }
                
                // PatologiaPersonal 
                if (consulta.getPatologiaPersonal() != null) {
                    PatologiaPersonal p = consulta.getPatologiaPersonal();
                    dto.setAnemia(p.getAnemia());
                    dto.setCardiopatias(p.getCardiopatias());
                    dto.setEnfGastricos(p.getEnfGastricos());
                    dto.setHepatitis(p.getHepatitis());
                    dto.setTuberculosis(p.getTuberculosis());
                    dto.setAsma(p.getAsma());
                    dto.setDiabetesMel(p.getDiabetesMel());
                    dto.setEpilepsia(p.getEpilepsia());
                    dto.setHipertension(p.getHipertension());
                    dto.setOtros(p.getOtros());
                    dto.setNinguno(p.getNinguno());
                    dto.setAlergias(p.getAlergias());
                    dto.setEmbarazo(p.getEmbarazo());
                    dto.setSemanaEmbarazo(p.getSemanaEmbarazo());
                    System.out.println("✅ PatologiaPersonal cargado correctamente");
                } else {
                    System.out.println("ℹ️ PatologiaPersonal es null para esta consulta");
                }
                
                
                // TratamientoMedico
                if (consulta.getTratamientoMedico() != null) {
                    dto.setTratamientoMedico(consulta.getTratamientoMedico().getTratamientoMedico());
                    dto.setRecibeAlgunMedicamento(consulta.getTratamientoMedico().getRecibeAlgunMedicamento());
                    dto.setTuvoHemorragiaDental(consulta.getTratamientoMedico().getTuvoHemorragiaDental());
                    dto.setEspecifiqueHemorragia(consulta.getTratamientoMedico().getEspecifiqueHemorragia());
                    System.out.println("✅ TratamientoMedico cargado");
                } else {
                    System.out.println("❌ TratamientoMedico es null");
                }
                
                // ExamenExtraOral
                if (consulta.getExamenExtraOral() != null) {
                    dto.setAtm(consulta.getExamenExtraOral().getAtm());
                    dto.setGangliosLinfaticos(consulta.getExamenExtraOral().getGangliosLinfaticos());
                    dto.setRespirador(consulta.getExamenExtraOral().getRespirador());
                }
                
                // ExamenIntraOral
                if (consulta.getExamenIntraOral() != null) {
                    dto.setLabios(consulta.getExamenIntraOral().getLabios());
                    dto.setLengua(consulta.getExamenIntraOral().getLengua());
                    dto.setPaladar(consulta.getExamenIntraOral().getPaladar());
                    dto.setPisoDeLaBoca(consulta.getExamenIntraOral().getPisoDeLaBoca());
                    dto.setMucosaYugal(consulta.getExamenIntraOral().getMucosaYugal());
                    dto.setEncias(consulta.getExamenIntraOral().getEncias());
                    dto.setUtilizaProtesisDental(consulta.getExamenIntraOral().getUtilizaProtesisDental());
                }
                
                // AntecedentesBucodentales
                if (consulta.getAntecedentesBucodentales() != null) {
                    dto.setFechaRevision(consulta.getAntecedentesBucodentales().getFechaRevision());
                    dto.setHabitoFuma(consulta.getAntecedentesBucodentales().getFuma());
                    dto.setHabitoBebe(consulta.getAntecedentesBucodentales().getBebe());
                    dto.setOtrosHabitos(consulta.getAntecedentesBucodentales().getOtrosHabitos());
                    
                }


                
                // AntecedentesHigieneOral
                if (consulta.getAntecedentesHigieneOral() != null) {
                    dto.setUtilizaCepilloDental(consulta.getAntecedentesHigieneOral().getUtilizaCepilloDental());
                    dto.setUtilizaHiloDental(consulta.getAntecedentesHigieneOral().getUtilizaHiloDental());
                    dto.setUtilizaEnjuagueBucal(consulta.getAntecedentesHigieneOral().getUtilizaEnjuagueBucal());
                    dto.setFrecuenciaCepillo(consulta.getAntecedentesHigieneOral().getFrecuenciaCepillo());
                    dto.setSangradoEncias(consulta.getAntecedentesHigieneOral().getDuranteElCepillado() != null && 
                                         consulta.getAntecedentesHigieneOral().getDuranteElCepillado().contains("Sangrado"));
                    dto.setHigieneBucal(consulta.getAntecedentesHigieneOral().getHigieneBucal());
                } // ✅ CORRECCIÓN: Cerré esta llave que faltaba
                
                dtos.add(dto);
                System.out.println("✅ DTO mapeado correctamente para consulta ID: " + consulta.getIdConsulta());
            }
            
            System.out.println("🎉 Total de DTOs creados: " + dtos.size());
            return dtos;
            
        } catch (Exception e) {
            System.out.println("❌ Error en obtenerConsultasCompletasPorPaciente: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("Error al obtener consultas completas: " + e.getMessage(), e);
        }
    } 
}