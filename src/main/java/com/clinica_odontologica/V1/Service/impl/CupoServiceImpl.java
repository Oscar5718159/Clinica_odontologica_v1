package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Dao.CupoRepository;
import com.clinica_odontologica.V1.Model.Dto.TratamientoCupoDTO;
import com.clinica_odontologica.V1.Model.Entity.Cupos;
import com.clinica_odontologica.V1.Model.Entity.Materia;
import com.clinica_odontologica.V1.Model.Entity.Tratamiento;
import com.clinica_odontologica.V1.Service.CupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CupoServiceImpl implements CupoService {

    @Autowired
    private CupoRepository cupoRepository;



    @Override
    public List<Cupos> listarTodos() {
        return cupoRepository.findAll();
    }

    @Override
    public Optional<Cupos> obtenerPorId(Long id) {
        return cupoRepository.findById(id);
    }

    @Override
    public Cupos guardar(Cupos cupo) {
        return cupoRepository.save(cupo);
    }

    @Override
    public void eliminar(Long id) {
        cupoRepository.deleteById(id);
    }

    @Override
    public List<Cupos> obtenerPorMateria(Materia materia) {
        return cupoRepository.findByMateria(materia);
    }

    @Override
    public Optional<Cupos> obtenerPorMateriaYTratamiento(Materia materia, Tratamiento tratamiento) {
        return cupoRepository.findByMateriaAndTratamiento(materia, tratamiento);
    }

    @Override
    public List<TratamientoCupoDTO> obtenerTratamientosConCuposPorMateria(Long idMateria) {
        return cupoRepository.findTratamientosWithCuposByMateriaId(idMateria);
    }
}