package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Dao.RayosXRepository;
import com.clinica_odontologica.V1.Model.Entity.RayosX;
import com.clinica_odontologica.V1.Service.RayosXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RayosXServiceImpl implements RayosXService {

    @Autowired
    private RayosXRepository rayosXRepository;

    @Override
    public List<RayosX> listarTodos() {
        return rayosXRepository.findAll();
    }
}