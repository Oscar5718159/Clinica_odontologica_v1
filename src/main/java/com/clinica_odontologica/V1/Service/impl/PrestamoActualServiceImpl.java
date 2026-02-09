package com.clinica_odontologica.V1.Service.impl;
import com.clinica_odontologica.V1.Service.PrestamoActualService;
import com.clinica_odontologica.V1.Model.Entity.PrestamoActual;

import com.clinica_odontologica.V1.Model.Dao.PrestamoActualRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PrestamoActualServiceImpl implements PrestamoActualService {
    @Autowired
    private PrestamoActualRepository prestamoActualRepository;
    @Override
    public List<PrestamoActual> obtenerTodos() {
        return prestamoActualRepository.findAll();
    }

    @Override
    public Optional<PrestamoActual> obtenerPorId(Long id) {
        return prestamoActualRepository.findById(id);
    }

    @Override
    public PrestamoActual guardar(PrestamoActual prestamoActual) {
        return prestamoActualRepository.save(prestamoActual);
    }

    @Override
    public PrestamoActual actualizar(Long id, PrestamoActual prestamoActualActualizado) {

        prestamoActualActualizado.setId(id);
        return prestamoActualRepository.save(prestamoActualActualizado);
    }

    @Override
    public void eliminar(Long id) {
        prestamoActualRepository.deleteById(id);
    }   
    @Override
    public List<PrestamoActual> buscarPorIdEstudiante(Long idEstudiante) {
        return prestamoActualRepository.findByIdEstudiante(idEstudiante);
    }   
    @Override
    public List<PrestamoActual> buscarPorIdArchivo(Long idArchivo) {
        return prestamoActualRepository.findByIdArchivo(idArchivo);
    }
}
