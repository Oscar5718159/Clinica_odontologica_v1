package com.clinica_odontologica.V1.Service.impl;

import com.clinica_odontologica.V1.Model.Entity.Usuario;
import com.clinica_odontologica.V1.Model.Dao.UsuarioRepository;
import com.clinica_odontologica.V1.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // ========== CRUD BÁSICO ==========
    @Override
    @Transactional(readOnly = true)
    public List<Usuario> obtenerTodos() {
        return usuarioRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> obtenerPorId(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario guardar(Usuario usuario) {
        if (usuarioRepository.existsByNombreUsuario(usuario.getNombreUsuario())) {
            throw new RuntimeException("El nombre de usuario '" + usuario.getNombreUsuario() + "' ya está en uso");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario actualizar(Long id, Usuario usuarioActualizado) {
        return usuarioRepository.findById(id)
                .map(usuario -> {
                    if (!usuario.getNombreUsuario().equals(usuarioActualizado.getNombreUsuario()) &&
                        usuarioRepository.existsByNombreUsuario(usuarioActualizado.getNombreUsuario())) {
                        throw new RuntimeException("El nombre de usuario '" + usuarioActualizado.getNombreUsuario() + "' ya está en uso");
                    }
                    usuario.setNombreUsuario(usuarioActualizado.getNombreUsuario());
                    usuario.setContraseña(usuarioActualizado.getContraseña());
                    usuario.setEstado(usuarioActualizado.getEstado());
                    usuario.setPersona(usuarioActualizado.getPersona());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con id: " + id));
    }

    @Override
    public void eliminar(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new RuntimeException("Usuario no encontrado con id: " + id);
        }
        usuarioRepository.deleteById(id);
    }

    // ========== MÉTODOS FALTANTES - AGREGADOS ==========
    @Override
    @Transactional(readOnly = true)
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.findByNombreUsuario(nombreUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public Boolean existePorNombreUsuario(String nombreUsuario) {
        return usuarioRepository.existsByNombreUsuario(nombreUsuario);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorEstado(Boolean estado) {
        return usuarioRepository.findByEstado(estado);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Usuario> buscarPorPersonaId(Long idPersona) {
        // ✅ USA EL MÉTODO CON @Query
        return usuarioRepository.buscarPorIdPersona(idPersona);
    }

}