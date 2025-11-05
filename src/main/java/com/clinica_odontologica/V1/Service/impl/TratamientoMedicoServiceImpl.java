package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.TratamientoMedico;
import com.clinica_odontologica.V1.Model.Dao.TratamientoMedicoRepository;
import com.clinica_odontologica.V1.Service.TratamientoMedicoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class TratamientoMedicoServiceImpl implements TratamientoMedicoService {

    @Autowired
    private TratamientoMedicoRepository tratamientoMedicoRepository;

    @Override
    public List<TratamientoMedico> obtenerTodos() {
        return tratamientoMedicoRepository.findAll();
    }

    @Override
    public Optional<TratamientoMedico> obtenerPorId(Long id) {
        return tratamientoMedicoRepository.findById(id);
    }

    @Override
    public TratamientoMedico guardar(TratamientoMedico tratamientoMedico) {
        return tratamientoMedicoRepository.save(tratamientoMedico);
    }

    @Override
    public void eliminar(Long id) {
        tratamientoMedicoRepository.deleteById(id);
    }
}