package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Tratamiento;
import java.util.List;
import java.util.Optional;
public interface TratamientoService {

        List<Tratamiento> obtenerTodos() ;
        
        Optional<Tratamiento> obtenerPorId(Long id);
        
        Tratamiento guardar(Tratamiento tratamiento);
        
        void eliminar(Long id);
        List<Tratamiento> obtenerPorTipoTratamiento(Long idTipoTratamiento);
}
