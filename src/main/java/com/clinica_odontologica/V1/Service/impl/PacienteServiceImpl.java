package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Paciente;
import com.clinica_odontologica.V1.Model.Entity.Persona;
import com.clinica_odontologica.V1.Model.Dao.PacienteRepository;
import com.clinica_odontologica.V1.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteServiceImpl implements PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Override
    public List<Paciente> obtenerTodos() {
        return pacienteRepository.findAll();
    }

    @Override
    public Optional<Paciente> obtenerPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    @Override
    public Paciente guardar(Paciente paciente) {
        return pacienteRepository.save(paciente);
    }

    @Override
    public Paciente actualizar(Long id, Paciente pacienteActualizado) {
        return pacienteRepository.findById(id)
            .map(paciente -> {
                // Actualizar campos específicos de Paciente
                paciente.setHistorialClinico(pacienteActualizado.getHistorialClinico());
                paciente.setLugarNacimiento(pacienteActualizado.getLugarNacimiento());
                paciente.setFechaNacimiento(pacienteActualizado.getFechaNacimiento());
                paciente.setOcupacion(pacienteActualizado.getOcupacion());
                paciente.setDireccion(pacienteActualizado.getDireccion());
                paciente.setTelefono(pacienteActualizado.getTelefono());
                paciente.setEstadoCivil(pacienteActualizado.getEstadoCivil());
                paciente.setNacionesOriginarias(pacienteActualizado.getNacionesOriginarias());
                paciente.setIdioma(pacienteActualizado.getIdioma());
                paciente.setCi(pacienteActualizado.getCi());
                
                // Actualizar datos de Persona (a través de la relación)
                if (pacienteActualizado.getPersona() != null) {
                    Persona persona = paciente.getPersona();
                    Persona personaActualizada = pacienteActualizado.getPersona();
                    
                    persona.setNombre(personaActualizada.getNombre());
                    persona.setApellido(personaActualizada.getApellido());
                }
                
                return pacienteRepository.save(paciente);
            })
            .orElseThrow(() -> new RuntimeException("Paciente no encontrado con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        pacienteRepository.deleteById(id);
    }

    @Override
    public List<Paciente> buscarPorOcupacion(String ocupacion) {
        return pacienteRepository.findByOcupacion(ocupacion);
    }

    @Override
    public List<Paciente> buscarPorLugarNacimiento(String lugarNacimiento) {
        return pacienteRepository.findByLugarNacimientoContainingIgnoreCase(lugarNacimiento);
    }

    @Override
    public List<Paciente> buscarPorEstadoCivil(String estadoCivil) {
        return pacienteRepository.findByEstadoCivil(estadoCivil);
    }

    @Override
    public List<Paciente> buscarPorNombre(String nombre) {
        return pacienteRepository.findByPersonaNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Paciente> buscarPorApellido(String apellido) {
        return pacienteRepository.findByPersonaApellidoContainingIgnoreCase(apellido);
    }

    @Override
    public Optional<Paciente> buscarPorHistorialClinico(String historialClinico) {
        return pacienteRepository.findByHistorialClinico(historialClinico);
    }

    // ✅ MÉTODO ACTUALIZADO: Usa la consulta personalizada del repositorio
    @Override
    public List<Paciente> buscarPorNombreCompleto(String nombreCompleto) {
        return pacienteRepository.buscarPorNombreCompleto(nombreCompleto);
    }

    @Override
    public List<Paciente> buscarPorNombreCompletoExacto(String nombre, String apellido) {
        return pacienteRepository.buscarPorNombreCompletoExacto(nombre, apellido);
    }
}