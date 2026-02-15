package com.clinica_odontologica.V1.Model.Dao;
import com.clinica_odontologica.V1.Model.Entity.PrestamoActual;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.*;

public interface PrestamoActualRepository extends JpaRepository<PrestamoActual, Long> {
    List<PrestamoActual> findByIdEstudiante(Long idEstudiante);
    List<PrestamoActual> findByIdArchivo(Long idArchivo);
    List<PrestamoActual> findByTipoPrestamo(String tipoPrestamo);
    List<PrestamoActual> findByFechaPrestamo(Date fechaPrestamo);
    List<PrestamoActual> findByFechaLimitePrestamo(Date fechaLimitePrestamo);
    List<PrestamoActual> findByEncargadoPrestamo(String encargadoPrestamo);
    List<PrestamoActual> findByFechaDevolucion(Date fechaDevolucion);
    List<PrestamoActual> findByTipoPrestamoContainingIgnoreCase(String tipoPrestamo);
    List<PrestamoActual> findByEncargadoPrestamoContainingIgnoreCase(String encargadoPrestamo);
  
}
