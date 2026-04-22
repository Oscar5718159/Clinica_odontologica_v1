package com.clinica_odontologica.V1.Service.impl;
import com.clinica_odontologica.V1.Model.Entity.Tratamiento;
import com.clinica_odontologica.V1.Model.Dao.TratamientoRepository;
import com.clinica_odontologica.V1.Service.TratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class TratamientoServiceImpl implements TratamientoService {
    
    @Autowired
    private TratamientoRepository tratamientoRepository;


    @Override
    public List<Tratamiento> obtenerTodos() {
        return tratamientoRepository.findAll();
    }

    @Override
    public Optional<Tratamiento> obtenerPorId(Long id) {
        return tratamientoRepository.findById(id);
    }

    @Override
    public Tratamiento guardar(Tratamiento tratamiento) {
        return tratamientoRepository.save(tratamiento);
    }

    @Override
    public void eliminar(Long id) {
        tratamientoRepository.deleteById(id);
    }   


    public List<Tratamiento> obtenerPorTipoTratamiento(Long idTipoTratamiento) {
    return tratamientoRepository.findByTipoTratamientoIdTipoTratamiento(idTipoTratamiento);


}
}
