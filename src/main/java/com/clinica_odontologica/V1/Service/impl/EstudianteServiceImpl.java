package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Cupos;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Dao.CupoRepository;
import com.clinica_odontologica.V1.Model.Dao.EstudianteRepository;
import com.clinica_odontologica.V1.Model.Dao.InscripcionMateriaRepository;
import com.clinica_odontologica.V1.Model.Dao.TratamientoRealizadoRepository;
import com.clinica_odontologica.V1.Service.EstudianteService;
import com.clinica_odontologica.V1.Model.Dto.ProgresoMateriaDTO;
import com.clinica_odontologica.V1.Model.Dto.ProgresoTratamientoDTO;
import com.clinica_odontologica.V1.Model.Entity.InscripcionMateria;
import com.clinica_odontologica.V1.Model.Entity.Materia;
import com.clinica_odontologica.V1.Model.Entity.Tratamiento;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;
@Service
public class EstudianteServiceImpl implements EstudianteService {

    @Autowired
    private EstudianteRepository estudianteRepository;
    @Autowired
    private InscripcionMateriaRepository inscripcionMateriaRepository;

    @Autowired
    private CupoRepository cupoRepository;

    @Autowired
    private TratamientoRealizadoRepository tratamientoRealizadoRepository;
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
    @Override
    public List<ProgresoMateriaDTO> obtenerProgresoPorEstudiante(Long idEstudiante) {
        // Verificar que el estudiante exista
        estudianteRepository.findById(idEstudiante)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        // Obtener inscripciones activas (estado 'cursando')
        List<InscripcionMateria> inscripciones = inscripcionMateriaRepository.findInscripcionesActivasByEstudiante(idEstudiante);

        List<ProgresoMateriaDTO> resultado = new ArrayList<>();

        for (InscripcionMateria inscripcion : inscripciones) {
            Materia materia = inscripcion.getMateria();
            List<Cupos> cuposList = cupoRepository.findByMateria(materia);

            List<ProgresoTratamientoDTO> tratamientosProgreso = new ArrayList<>();
            for (Cupos cupo : cuposList) {
                Tratamiento tratamiento = cupo.getTratamiento();
                Integer requerido = cupo.getCuposDisponibles();
                Integer realizado = tratamientoRealizadoRepository.sumCantidadValidadosByInscripcionAndTratamiento(inscripcion, tratamiento);
                    if (realizado == null) realizado = 0;
                tratamientosProgreso.add(new ProgresoTratamientoDTO(
                        tratamiento.getIdTratamiento(),
                        tratamiento.getNombreTratamiento(),
                        requerido,
                        (int) realizado
                ));
            }

            ProgresoMateriaDTO materiaDTO = new ProgresoMateriaDTO();
            materiaDTO.setIdMateria(materia.getId_materia());
            materiaDTO.setNombreMateria(materia.getNombreMateria());
            materiaDTO.setTratamientos(tratamientosProgreso);

            resultado.add(materiaDTO);
        }

        return resultado;
    }
}