package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Persona;
import com.clinica_odontologica.V1.Model.Dao.PersonaRepository;
import com.clinica_odontologica.V1.Service.PersonaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class PersonaServiceImpl implements PersonaService {

    @Autowired
    private PersonaRepository personaRepository;

    @Override
    public List<Persona> obtenerTodos() {
        return personaRepository.findAll();
    }

    @Override
    public Optional<Persona> obtenerPorId(Long id) {
        return personaRepository.findById(id);
    }

    @Override
    public Persona guardar(Persona persona) {
        return personaRepository.save(persona);
    }

    @Override
    public Persona actualizar(Long id, Persona personaActualizada) {
        return personaRepository.findById(id)
            .map(persona -> {
                persona.setNombre(personaActualizada.getNombre());
                persona.setApellido(personaActualizada.getApellido());
                persona.setEdad(personaActualizada.getEdad());
                persona.setSexo(personaActualizada.getSexo());
                return personaRepository.save(persona);
            })
            .orElseThrow(() -> new RuntimeException("Persona no encontrada con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        personaRepository.deleteById(id);
    }

    @Override
    public List<Persona> buscarPorNombre(String nombre) {
        return personaRepository.findByNombreContainingIgnoreCase(nombre);
    }

    @Override
    public List<Persona> buscarPorApellido(String apellido) {
        return personaRepository.findByApellidoContainingIgnoreCase(apellido);
    }

    @Override
    public List<Persona> buscarPorEdad(Integer edad) {
        return personaRepository.findByEdad(edad);
    }

    @Override
    public List<Persona> buscarPorSexo(Character sexo) {
        return personaRepository.findBySexo(sexo);
    }

    @Override
    public List<Persona> buscarPorNombreYApellido(String nombre, String apellido) {
        return personaRepository.findByNombreAndApellido(nombre, apellido);
    }
}