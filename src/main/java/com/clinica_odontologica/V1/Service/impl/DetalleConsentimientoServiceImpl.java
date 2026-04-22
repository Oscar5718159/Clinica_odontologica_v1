package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Dao.DetalleConsentimientoRepository;
import com.clinica_odontologica.V1.Model.Entity.Consentimiento;
import com.clinica_odontologica.V1.Model.Entity.DetalleConsentimiento;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Service.ConsentimientoService;
import com.clinica_odontologica.V1.Service.DetalleConsentimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DetalleConsentimientoServiceImpl implements DetalleConsentimientoService {

    @Autowired
    private DetalleConsentimientoRepository detalleRepository;

    @Autowired
    private ConsentimientoService consentimientoService;

    @Override
    public List<DetalleConsentimiento> listarTodos() {
        return detalleRepository.findAll();
    }

    @Override
    public Optional<DetalleConsentimiento> obtenerPorId(Long id) {
        return detalleRepository.findById(id);
    }

    @Override
    public DetalleConsentimiento guardar(DetalleConsentimiento detalle) {
        return detalleRepository.save(detalle);
    }

    @Override
    public void eliminar(Long id) {
        detalleRepository.deleteById(id);
    }

    @Override
    public List<DetalleConsentimiento> obtenerPorConsentimiento(Long consentimientoId) {
        Optional<Consentimiento> consentimiento = consentimientoService.obtenerPorId(consentimientoId);
        return consentimiento.map(detalleRepository::findByConsentimiento).orElse(List.of());
    }

    @Override
    public List<DetalleConsentimiento> obtenerPorEstudianteAsignadoYEstado(Estudiante estudiante, Boolean estado) {
        // Aquí necesitas decidir cómo filtrar por estudiante asignado.
        // En tu entidad no hay campo estudianteAsignado. Si quieres esa funcionalidad,
        // debes agregarlo a la entidad. Por ahora, si no lo tienes, puedes implementar
        // una búsqueda por estado solamente y luego filtrar en Java, o crear un método
        // en el repositorio que reciba estudianteId (si la entidad tiene estudianteAsignado_id).
        // Como no lo tiene, quizás esta funcionalidad no es necesaria aún.
        // Por simplicidad, dejaré que busque solo por estado.
        return detalleRepository.findByEstadoDetalle(estado);
    }
    @Override
    public void actualizarEstado(Long id, Boolean nuevoEstado) {
        detalleRepository.findById(id).ifPresent(detalle -> {
            detalle.setEstadoDetalle(nuevoEstado);
            detalleRepository.save(detalle);
        });
    }
    @Override
    public List<DetalleConsentimiento> listarPendientes() {
        return detalleRepository.findByEstadoDetalleTrue();
    }

}