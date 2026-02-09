package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Archivo;
import java.util.List;
import java.util.Optional;

public interface ArchivoService {
    List<Archivo> getAllArchivos();
    Optional<Archivo> getArchivoById(Long id);
    Archivo saveArchivo(Archivo archivo);
    void deleteArchivo(Long id);

    // ✅ Método principal para buscar archivo por CI O nombre
    Optional<Archivo> findArchivoByPacienteCiOrNombre(Integer ci, String nombre);
    
    // ✅ Método para obtener solo el ID
    Optional<Long> findIdArchivoByPacienteCiOrNombre(Integer ci, String nombre);
    
    // ✅ Verificar si paciente tiene archivo
    boolean pacienteTieneArchivo(Long idPaciente);
}