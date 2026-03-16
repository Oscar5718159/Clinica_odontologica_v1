package com.clinica_odontologica.V1.Service;
import com.clinica_odontologica.V1.Model.Entity.SolicitudInsumo;
import com.clinica_odontologica.V1.Model.Dao.SolicitudInsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class SolicitudInsumoService {
    @Autowired
    private SolicitudInsumoRepository repository;

    public List<SolicitudInsumo> guardarTodos(List<SolicitudInsumo> insumos) {
        return repository.saveAll(insumos);
    }

    // Otros métodos si son necesarios
}