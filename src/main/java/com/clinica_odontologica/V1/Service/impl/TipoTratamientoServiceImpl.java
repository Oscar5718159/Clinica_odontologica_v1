package com.clinica_odontologica.V1.Service.impl;
import com.clinica_odontologica.V1.Model.Entity.TipoTratamiento;
import com.clinica_odontologica.V1.Model.Dao.TipoTratamientoRepository;
import com.clinica_odontologica.V1.Service.TipoTratamientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service

public class TipoTratamientoServiceImpl implements TipoTratamientoService {
    
    @Autowired
    private TipoTratamientoRepository tipoTratamientoRepository;

    @Override
    public List<TipoTratamiento> obtenerTodos() {
        return tipoTratamientoRepository.findAll();
    }

    @Override
    public Optional<TipoTratamiento> obtenerPorId(Long id) {
        return tipoTratamientoRepository.findById(id);
    }

    @Override
    public TipoTratamiento guardar(TipoTratamiento tipoTratamiento) {
        return tipoTratamientoRepository.save(tipoTratamiento);
    }

    @Override
    public void eliminar(Long id) {
        tipoTratamientoRepository.deleteById(id);
    }

    @Override

    public List<TipoTratamiento> obtenerPorClinica(Long idClinica) {
        return tipoTratamientoRepository.findByClinicaIdClinica(idClinica);
    }

}
