package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Consentimiento;
import com.clinica_odontologica.V1.Model.Dao.ConsentimientoRepository;
import com.clinica_odontologica.V1.Service.ConsentimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ConsentimientoServiceImpl implements ConsentimientoService {

    @Autowired
    private ConsentimientoRepository consentimientoRepository;

    @Override
    public List<Consentimiento> obtenerTodos() {
        return consentimientoRepository.findByEstadoTrue();
    }

    @Override
    public Optional<Consentimiento> obtenerPorId(Long id) {
        return consentimientoRepository.findById(id);
    }

    @Override
    public Optional<Consentimiento> obtenerPorConsulta(Long idConsulta) {
        return consentimientoRepository.findByConsultaIdConsulta(idConsulta);
    }

    @Override
    public Consentimiento guardar(Consentimiento consentimiento) {
        return consentimientoRepository.save(consentimiento);
    }

    @Override
    public void eliminar(Long id) {
        Optional<Consentimiento> consentimiento = consentimientoRepository.findById(id);
        if (consentimiento.isPresent()) {
            Consentimiento cons = consentimiento.get();
            cons.setEstado(false);
            consentimientoRepository.save(cons);
        }
    }

    @Override
    public boolean existePorConsulta(Long idConsulta) {
        return consentimientoRepository.existsByConsultaIdConsulta(idConsulta);
    }

    @Override
    public List<Consentimiento> obtenerPorPaciente(Long idPaciente) {
        return consentimientoRepository.findByPacienteId(idPaciente);
    }
}