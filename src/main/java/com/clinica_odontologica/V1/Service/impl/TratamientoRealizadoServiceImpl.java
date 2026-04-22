package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Dao.TratamientoRealizadoRepository;
import com.clinica_odontologica.V1.Model.Entity.*;
import com.clinica_odontologica.V1.Service.CupoService;
import com.clinica_odontologica.V1.Service.TratamientoRealizadoService;
import org.springframework.beans.factory.annotation.Autowired;
import com.clinica_odontologica.V1.Service.DocenteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class TratamientoRealizadoServiceImpl implements TratamientoRealizadoService {

    @Autowired
    private TratamientoRealizadoRepository tratamientoRealizadoRepository;

    @Autowired
    private CupoService cupoService;

    @Autowired
    private DocenteService docenteService;

    @Override
    public List<TratamientoRealizado> listarTodos() {
        return tratamientoRealizadoRepository.findAll();
    }

    @Override
    public Optional<TratamientoRealizado> obtenerPorId(Long id) {
        return tratamientoRealizadoRepository.findById(id);
    }

    @Override
    public TratamientoRealizado guardar(TratamientoRealizado tratamientoRealizado) {
        return tratamientoRealizadoRepository.save(tratamientoRealizado);
    }

    @Override
    public void eliminar(Long id) {
        tratamientoRealizadoRepository.deleteById(id);
    }

    @Override
    public List<TratamientoRealizado> obtenerPorInscripcion(InscripcionMateria inscripcion) {
        return tratamientoRealizadoRepository.findByInscripcionMateria(inscripcion);
    }

    @Override
    public List<TratamientoRealizado> obtenerValidadosPorInscripcion(InscripcionMateria inscripcion) {
        return tratamientoRealizadoRepository.findByInscripcionMateriaAndValidacionTrue(inscripcion);
    }

    @Override
    public boolean cumpleCuposMateria(InscripcionMateria inscripcion) {
        Materia materia = inscripcion.getMateria();
        List<Cupos> cupos = cupoService.obtenerPorMateria(materia);
        for (Cupos cupo : cupos) {
            long realizados = tratamientoRealizadoRepository.countValidadosByInscripcionAndTratamiento(inscripcion, cupo.getTratamiento());
            if (realizados < cupo.getCuposDisponibles()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Optional<TratamientoRealizado> findByDetalleConsentimiento(DetalleConsentimiento detalle) {
        return tratamientoRealizadoRepository.findByDetalleConsentimiento(detalle);
    }
    @Override
    @Transactional
    public void validarTratamiento(Long id, Long docenteId, Integer cantidadReal) {
        TratamientoRealizado tr = tratamientoRealizadoRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Tratamiento no encontrado"));
        if (tr.getValidacion()) {
            throw new RuntimeException("Ya fue validado anteriormente");
        }
        
        // Obtener el docente existente desde la base de datos
        Docente docente = docenteService.obtenerPorId(docenteId)
            .orElseThrow(() -> new RuntimeException("Docente no encontrado"));
        
        tr.setCantidadTratamiento(cantidadReal);
        tr.setValidacion(true);
        tr.setFechaValidacion(LocalDate.now());
        tr.setDocente(docente);  // Ahora usas el objeto obtenido
        tratamientoRealizadoRepository.save(tr);
    }
}