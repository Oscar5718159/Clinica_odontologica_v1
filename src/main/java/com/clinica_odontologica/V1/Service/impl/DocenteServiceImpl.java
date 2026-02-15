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

    // ✅ IMPLEMENTACIÓN DEL NUEVO MÉTODO
    @Override
    public boolean existePorCodigoDocente(Integer codigo) {
        return docenteRepository.findAll().stream()
                .anyMatch(docente -> docente.getCodigoDocente().equals(codigo));
    }
}