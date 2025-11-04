package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.ExamenIntraOral;
import com.clinica_odontologica.V1.Model.Dao.ExamenIntraOralRepository;
import com.clinica_odontologica.V1.Service.ExamenIntraOralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ExamenIntraOralServiceImpl implements ExamenIntraOralService {

    @Autowired
    private ExamenIntraOralRepository examenIntraOralRepository;

    @Override
    public List<ExamenIntraOral> obtenerTodos() {
        return examenIntraOralRepository.findAll();
    }

    @Override
    public Optional<ExamenIntraOral> obtenerPorId(Long id) {
        return examenIntraOralRepository.findById(id);
    }

    @Override
    public ExamenIntraOral guardar(ExamenIntraOral examenIntraOral) {
        return examenIntraOralRepository.save(examenIntraOral);
    }

    @Override
    public void eliminar(Long id) {
        examenIntraOralRepository.deleteById(id);
    }
}