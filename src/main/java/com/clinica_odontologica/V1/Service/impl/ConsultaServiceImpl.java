package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Consulta;
import com.clinica_odontologica.V1.Model.Dao.ConsultaRepository;
import com.clinica_odontologica.V1.Service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaServiceImpl implements ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

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