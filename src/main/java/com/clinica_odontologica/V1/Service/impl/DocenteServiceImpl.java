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
        try {
            List<Docente> docentes = docenteRepository.findAll();
            
            // DEBUG DETALLADO
            System.out.println("=== DEBUG DocenteService.obtenerTodos() ===");
            System.out.println("📊 Total de docentes encontrados: " + docentes.size());
            System.out.println("🔢 Total en BD: " + docenteRepository.countTotalDocentes());
            System.out.println("✅ Activos en BD: " + docenteRepository.countDocentesActivos());
            
            if (docentes.isEmpty()) {
                System.out.println("⚠️ La lista de docentes está VACÍA");
            } else {
                docentes.forEach(d -> {
                    System.out.println("👤 Docente: ID=" + d.getIdDocente() + 
                                     ", Nombre=" + d.getNombreDocente() + 
                                     ", Especialidad=" + d.getEspecialidad() + 
                                     ", Estado=" + d.getEstado());
                });
            }
            System.out.println("=== FIN DEBUG ===");
            
            return docentes;
        } catch (Exception e) {
            System.err.println("❌ ERROR en obtenerTodos: " + e.getMessage());
            e.printStackTrace();
            throw e; // Relanza la excepción para ver el error completo
        }
    }

    @Override
    public List<Docente> obtenerTodosActivos() {
        return docenteRepository.findByEstadoTrue();
    }

    // ... los demás métodos permanecen igual
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