package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Archivo;
import com.clinica_odontologica.V1.Model.Dao.ArchivoRepository;
import com.clinica_odontologica.V1.Service.ArchivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ArchivoServiceImpl implements ArchivoService {

    @Autowired
    private ArchivoRepository archivoRepository;

    @Override
    public List<Archivo> getAllArchivos() {
        return archivoRepository.findAll();
    }

    @Override
    public Optional<Archivo> getArchivoById(Long id) {
        return archivoRepository.findById(id);
    }

    @Override
    public Archivo saveArchivo(Archivo archivo) {
        return archivoRepository.save(archivo);
    }

    @Override
    public void deleteArchivo(Long id) {
        archivoRepository.deleteById(id);
    }


    
 @Override
    public Optional<Archivo> findArchivoByPacienteCiOrNombre(Integer ci, String nombre) {
        if ((ci == null || ci == 0) && (nombre == null || nombre.trim().isEmpty())) {
            throw new IllegalArgumentException("Debe proporcionar CI o nombre del paciente");
        }
        
        // Limpiar el nombre para búsqueda
        String nombreBusqueda = nombre != null ? nombre.trim() : null;
        
        return archivoRepository.findArchivoByPacienteCiOrNombre(ci, nombreBusqueda);
    }
    
    // ✅ Método para obtener solo el ID
    @Override
    public Optional<Long> findIdArchivoByPacienteCiOrNombre(Integer ci, String nombre) {
        if ((ci == null || ci == 0) && (nombre == null || nombre.trim().isEmpty())) {
            throw new IllegalArgumentException("Debe proporcionar CI o nombre del paciente");
        }
        
        // Limpiar el nombre para búsqueda
        String nombreBusqueda = nombre != null ? nombre.trim() : null;
        
        return archivoRepository.findIdArchivoByPacienteCiOrNombre(ci, nombreBusqueda);
    }
    
    // ✅ Verificar si paciente tiene archivo
    @Override
    public boolean pacienteTieneArchivo(Long idPaciente) {
        if (idPaciente == null) {
            return false;
        }
        return archivoRepository.existsByPacienteId(idPaciente);
    }
    
    // ... otros métodos si los necesitas ...
}