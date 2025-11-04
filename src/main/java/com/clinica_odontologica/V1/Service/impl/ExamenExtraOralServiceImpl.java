package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.ExamenExtraOral;
import com.clinica_odontologica.V1.Model.Dao.ExamenExtraOralRepository;
import com.clinica_odontologica.V1.Service.ExamenExtraOralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExamenExtraOralServiceImpl implements ExamenExtraOralService {

    @Autowired
    private ExamenExtraOralRepository examenExtraOralRepository;

    @Override
    public List<ExamenExtraOral> obtenerTodos() {
        return examenExtraOralRepository.findAll();
    }

    @Override
    public Optional<ExamenExtraOral> obtenerPorId(Long id) {
        return examenExtraOralRepository.findById(id);
    }

    @Override
    public ExamenExtraOral guardar(ExamenExtraOral examenExtraOral) {
        return examenExtraOralRepository.save(examenExtraOral);
    }

    @Override
    public void eliminar(Long id) {
        examenExtraOralRepository.deleteById(id);
    }
}