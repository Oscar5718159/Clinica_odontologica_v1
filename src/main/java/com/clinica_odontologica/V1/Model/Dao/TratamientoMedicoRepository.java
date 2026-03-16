package com.clinica_odontologica.V1.Model.Dao;

import com.clinica_odontologica.V1.Model.Entity.TratamientoMedico;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TratamientoMedicoRepository extends JpaRepository<TratamientoMedico, Long> {
    
    List<TratamientoMedico> findByTratamientoMedico(String tratamientoMedico);
    
    List<TratamientoMedico> findByRecibeAlgunMedicamento(String recibeAlgunMedicamento);
    
    List<TratamientoMedico> findByTuvoHemorragiaDental(Boolean tuvoHemorragiaDental);
}