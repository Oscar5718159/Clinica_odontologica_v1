package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Dao.MateriaRepository;
import com.clinica_odontologica.V1.Model.Entity.Clinica;
import com.clinica_odontologica.V1.Model.Entity.Materia;
import com.clinica_odontologica.V1.Service.MateriaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class MateriaServiceImpl implements MateriaService {

    @Autowired
    private MateriaRepository materiaRepository;

    @Override
    public List<Materia> listarTodos() {
        return materiaRepository.findAll();
    }

    @Override
    public Optional<Materia> obtenerPorId(Long id) {
        return materiaRepository.findById(id);
    }

    @Override
    public Materia guardar(Materia materia) {
        return materiaRepository.save(materia);
    }

    @Override
    public void eliminar(Long id) {
        materiaRepository.deleteById(id);
    }

    @Override
    public List<Materia> obtenerPorClinica(Clinica clinica) {
        return materiaRepository.findByClinica(clinica);
    }
}