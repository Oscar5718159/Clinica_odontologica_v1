package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.Informante;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface InformanteRepository extends JpaRepository<Informante, Long> {
    
    List<Informante> findByNombresContainingIgnoreCase(String nombres);
    
    List<Informante> findByApellidoPaternoContainingIgnoreCase(String apellidoPaterno);
    
    List<Informante> findByApellidoMaternoContainingIgnoreCase(String apellidoMaterno);
    
    List<Informante> findByDireccionContainingIgnoreCase(String direccion);
    
    List<Informante> findByTelefonoContainingIgnoreCase(String telefono);
}