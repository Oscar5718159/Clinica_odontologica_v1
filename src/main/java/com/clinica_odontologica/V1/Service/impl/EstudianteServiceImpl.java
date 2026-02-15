package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Dao.EstudianteRepository;
import com.clinica_odontologica.V1.Service.EstudianteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Override
    public List<Estudiante> obtenerTodos() {
        return estudianteRepository.findAll();
    }

    @Override
    public Optional<Estudiante> obtenerPorId(Long id) {
        return estudianteRepository.findById(id);
    }

    @Override
    public Estudiante guardar(Estudiante estudiante) {
        return estudianteRepository.save(estudiante);
    }

    @Override
    public List<Estudiante> buscarPorCodigoEstudiante(Integer codigo) {
        return estudianteRepository.findAllByCodigoEstudiante(codigo);
    }

    // ========== NUEVOS MÉTODOS ==========
    @Override
    @Transactional(readOnly = true)
    public Optional<Estudiante> buscarPorCodigoExacto(Integer codigoEstudiante) {
        return estudianteRepository.findByCodigoEstudiante(codigoEstudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existePorCodigoEstudiante(Integer codigoEstudiante) {
        return estudianteRepository.existsByCodigoEstudiante(codigoEstudiante);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> buscarPorGestion(String gestion) {
        return estudianteRepository.findByGestion(gestion);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Estudiante> buscarPorBloqueado(Boolean bloqueado) {
        return estudianteRepository.findByBloqueado(bloqueado);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Estudiante> buscarPorNombreUsuario(String username) {
        return estudianteRepository.buscarPorNombreUsuario(username);
    }
}