package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.PatologiaPersonal;
import com.clinica_odontologica.V1.Model.Dao.PatologiaPersonalRepository;
import com.clinica_odontologica.V1.Service.PatologiaPersonalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PatologiaPersonalServiceImpl implements PatologiaPersonalService {

    @Autowired
    private PatologiaPersonalRepository patologiaPersonalRepository;

    @Override
    public List<PatologiaPersonal> obtenerTodos() {
        return patologiaPersonalRepository.findAll();
    }

    @Override
    public Optional<PatologiaPersonal> obtenerPorId(Long id) {
        return patologiaPersonalRepository.findById(id);
    }

    @Override
    public PatologiaPersonal guardar(PatologiaPersonal patologiaPersonal) {
        return patologiaPersonalRepository.save(patologiaPersonal);
    }

    @Override
    public PatologiaPersonal actualizar(Long id, PatologiaPersonal patologiaPersonal) {
        Optional<PatologiaPersonal> existente = patologiaPersonalRepository.findById(id);
        if (existente.isPresent()) {
            PatologiaPersonal actualizado = existente.get();
            actualizado.setNombrePatologia(patologiaPersonal.getNombrePatologia());
            actualizado.setAlergias(patologiaPersonal.getAlergias());
            actualizado.setEmbarazo(patologiaPersonal.getEmbarazo());
            actualizado.setSemanaEmbarazo(patologiaPersonal.getSemanaEmbarazo());
            return patologiaPersonalRepository.save(actualizado);
        }
        throw new RuntimeException("PatologiaPersonal no encontrada con id: " + id);
    }

    @Override
    public void eliminar(Long id) {
        patologiaPersonalRepository.deleteById(id);
    }


}