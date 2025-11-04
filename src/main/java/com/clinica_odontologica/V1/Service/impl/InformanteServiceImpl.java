package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Informante;
import com.clinica_odontologica.V1.Model.Dao.InformanteRepository;
import com.clinica_odontologica.V1.Service.InformanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class InformanteServiceImpl implements InformanteService {

    @Autowired
    private InformanteRepository informanteRepository;

    @Override
    public List<Informante> obtenerTodos() {
        return informanteRepository.findAll();
    }

    @Override
    public Optional<Informante> obtenerPorId(Long id) {
        return informanteRepository.findById(id);
    }

    @Override
    public Informante guardar(Informante informante) {
        return informanteRepository.save(informante);
    }

    @Override
    public void eliminar(Long id) {
        informanteRepository.deleteById(id);
    }

}