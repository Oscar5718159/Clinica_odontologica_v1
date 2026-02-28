package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Usuario;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Dao.UsuarioRepository;
import com.clinica_odontologica.V1.Model.Dao.EstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    public Optional<Estudiante> autenticar(String username, String password) {
        // Buscar usuario por nombre de usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreUsuario(username);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // Verificar contraseña y estado activo
            if (usuario.getContraseña().equals(password) && usuario.getEstado()) {
                // Buscar el estudiante asociado a este usuario
                return estudianteRepository.findByUsuario(usuario);
            }
        }
        
        return Optional.empty();
    }
}