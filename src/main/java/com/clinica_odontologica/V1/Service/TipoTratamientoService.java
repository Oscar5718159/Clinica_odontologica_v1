package com.clinica_odontologica.V1.Service;
import com.clinica_odontologica.V1.Model.Entity.TipoTratamiento;
import java.util.List;
import java.util.Optional;
public interface TipoTratamientoService {
    
    List<TipoTratamiento> obtenerTodos();
    
    Optional<TipoTratamiento> obtenerPorId(Long id);
    
    TipoTratamiento guardar(TipoTratamiento tipoTratamiento);
    
    void eliminar(Long id);
    List<TipoTratamiento> obtenerPorClinica(Long idClinica);
}