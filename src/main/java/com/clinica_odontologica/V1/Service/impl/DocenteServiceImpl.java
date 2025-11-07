package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Docente;
import com.clinica_odontologica.V1.Model.Dao.DocenteRepository;
import com.clinica_odontologica.V1.Service.DocenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DocenteServiceImpl implements DocenteService {

    @Autowired
    private DocenteRepository docenteRepository;

    @Override
    public List<Docente> obtenerTodos() {
        return docenteRepository.findAll();
    }

    @Override
    public List<Docente> obtenerTodosActivos() {
        return docenteRepository.findByEstadoTrue();
    }

    @Override
    public Optional<Docente> obtenerPorId(Long id) {
        return docenteRepository.findById(id);
    }

    @Override
    public Docente guardar(Docente docente) {
        return docenteRepository.save(docente);
    }

    @Override
    public void eliminar(Long id) {
        Optional<Docente> docente = docenteRepository.findById(id);
        if (docente.isPresent()) {
            Docente doc = docente.get();
            doc.setEstado(false);
            docenteRepository.save(doc);
        }
    }

    @Override
    public List<Docente> buscarPorEspecialidad(String especialidad) {
        return docenteRepository.findByEspecialidadContainingIgnoreCase(especialidad);
    }

    @Override
    public List<Docente> buscarPorNombre(String nombreDocente) {
        return docenteRepository.findByNombreDocenteContainingIgnoreCase(nombreDocente);
    }

    @Override
    public List<Docente> buscarPorClinica(Long idClinica) {
        return docenteRepository.findByClinicaIdClinica(idClinica);
    }

    @Override
    public List<Docente> buscarActivosPorCriterio(String criterio) {
        return docenteRepository.buscarActivosPorCriterio(criterio);
    }
}