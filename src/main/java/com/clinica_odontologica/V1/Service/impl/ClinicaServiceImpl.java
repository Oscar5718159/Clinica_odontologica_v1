package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Clinica;
import com.clinica_odontologica.V1.Model.Dao.ClinicaRepository;
import com.clinica_odontologica.V1.Service.ClinicaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ClinicaServiceImpl implements ClinicaService {

    @Autowired
    private ClinicaRepository clinicaRepository;

    @Override
    public List<Clinica> obtenerTodas() {
        return clinicaRepository.findAll();
    }

    @Override
    public Optional<Clinica> obtenerPorId(Long id) {
        return clinicaRepository.findById(id);
    }

    @Override
    public Clinica guardar(Clinica clinica) {
        return clinicaRepository.save(clinica);
    }

    @Override
    public void eliminar(Long id) {
        clinicaRepository.deleteById(id);
    }

    @Override
    public List<Clinica> buscarPorNombre(String nombre) {
        return clinicaRepository.findByNombreClinicaContainingIgnoreCase(nombre);
    }

    @Override
    public List<Clinica> buscarPorTurno(String turno) {
        return clinicaRepository.findByTurno(turno);
    }
}