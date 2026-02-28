package com.clinica_odontologica.V1.Scheduler;

import com.clinica_odontologica.V1.Model.Entity.PrestamoActual;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Dao.PrestamoActualRepository;
import com.clinica_odontologica.V1.Model.Dao.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;

@Component
public class PrestamoScheduler {

    @Autowired
    private PrestamoActualRepository prestamoRepository;
    
    @Autowired
    private EstudianteRepository estudianteRepository;
    
    // Se ejecuta todos los días a las 00:00
    @Scheduled(cron = "0 0 0 * * *")
    @Transactional
    public void verificarPrestamosVencidos() {
        System.out.println("🔍 Verificando préstamos vencidos: " + new Date());
        
        List<PrestamoActual> prestamosVencidos = prestamoRepository.findPrestamosVencidos();
        
        for (PrestamoActual prestamo : prestamosVencidos) {
            // Marcar préstamo como vencido
            prestamo.setEstadoPrestamo("VENCIDO");
            prestamoRepository.save(prestamo);
            
            // Bloquear al estudiante
            Estudiante estudiante = estudianteRepository.findById(prestamo.getIdEstudiante())
                .orElse(null);
            
            if (estudiante != null) {
                estudiante.setBloqueado(true);
                estudianteRepository.save(estudiante);
                
                System.out.println("🚫 Estudiante " + prestamo.getIdEstudiante() + 
                                 " bloqueado por préstamo vencido ID: " + prestamo.getId());
            }
        }
    }
}