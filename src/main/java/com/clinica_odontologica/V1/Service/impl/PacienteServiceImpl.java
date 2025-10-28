package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Paciente;
import com.clinica_odontologica.V1.Model.Entity.Persona;
import com.clinica_odontologica.V1.Model.Dao.PacienteRepository;
import com.clinica_odontologica.V1.Service.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    // ✅ NUEVO MÉTODO PARA BÚSQUEDA POR NOMBRE COMPLETO
    @Override
    public List<Paciente> buscarPorNombreCompleto(String nombreCompleto) {
        // Dividir el nombre completo en partes
        String[] partes = nombreCompleto.trim().split("\\s+");
        
        if (partes.length == 0) {
            return List.of(); // Retorna lista vacía si no hay nombre
        }
        
        // Buscar por nombre (primera parte) y apellido (última parte)
        String nombre = partes[0];
        String apellido = partes[partes.length - 1];
        
        // Buscar pacientes que coincidan con el nombre Y el apellido
        List<Paciente> porNombre = pacienteRepository.findByPersonaNombreContainingIgnoreCase(nombre);
        List<Paciente> porApellido = pacienteRepository.findByPersonaApellidoContainingIgnoreCase(apellido);
        
        // Retornar la intersección (pacientes que están en ambas listas)
        return porNombre.stream()
                .filter(porApellido::contains)
                .collect(Collectors.toList());
    }
}