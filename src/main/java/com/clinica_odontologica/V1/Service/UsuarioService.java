package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Usuario;
import java.util.List;
import java.util.Optional;

public interface UsuarioService {
    
    // ========== CRUD BÁSICO ==========
    List<Usuario> obtenerTodos();
    Optional<Usuario> obtenerPorId(Long id);
    Usuario guardar(Usuario usuario);
    Usuario actualizar(Long id, Usuario usuarioActualizado);
    void eliminar(Long id);
    
    // ========== MÉTODOS QUE TE FALTAN ==========
    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);
    Boolean existePorNombreUsuario(String nombreUsuario);
    List<Usuario> buscarPorEstado(Boolean estado);
    List<Usuario> buscarPorPersonaId(Long idPersona);  // ✅ USA ESTE NOMBRE

}
