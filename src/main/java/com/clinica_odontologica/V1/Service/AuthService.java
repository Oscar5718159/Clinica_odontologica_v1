package com.clinica_odontologica.V1.Service;

import com.clinica_odontologica.V1.Model.Entity.Usuario;
import com.clinica_odontologica.V1.Model.Entity.Estudiante;
import com.clinica_odontologica.V1.Model.Entity.Docente;
import com.clinica_odontologica.V1.Model.Dao.UsuarioRepository;
import com.clinica_odontologica.V1.Model.Dao.EstudianteRepository;
import com.clinica_odontologica.V1.Model.Dao.DocenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AuthService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private EstudianteRepository estudianteRepository;

    @Autowired
    private DocenteRepository docenteRepository;

    /**
     * Autentica un usuario y determina su tipo
     * @return Un objeto con el usuario y su tipo
     */
    public Optional<AuthResult> autenticar(String username, String password) {
        // Buscar usuario por nombre de usuario
        Optional<Usuario> usuarioOpt = usuarioRepository.findByNombreUsuario(username);
        
        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            
            // Verificar contraseña y estado activo
            if (usuario.getContraseña().equals(password) && usuario.getEstado()) {
                
                // Verificar si es estudiante
                Optional<Estudiante> estudianteOpt = estudianteRepository.findByUsuario(usuario);
                if (estudianteOpt.isPresent()) {
                    return Optional.of(new AuthResult(usuario, estudianteOpt.get(), "ESTUDIANTE"));
                }
                
                // Verificar si es docente
                Optional<Docente> docenteOpt = docenteRepository.findByUsuario(usuario);
                if (docenteOpt.isPresent()) {
                    return Optional.of(new AuthResult(usuario, docenteOpt.get(), "DOCENTE"));
                }
                
                // Si es solo usuario (admin u otro tipo)
                return Optional.of(new AuthResult(usuario, null, "ADMIN"));
            }
        }
        
        return Optional.empty();
    }

    // Clase interna para el resultado
    public static class AuthResult {
        private Usuario usuario;
        private Object perfil; // Estudiante, Docente, etc.
        private String tipo;

        public AuthResult(Usuario usuario, Object perfil, String tipo) {
            this.usuario = usuario;
            this.perfil = perfil;
            this.tipo = tipo;
        }

        public Usuario getUsuario() { return usuario; }
        public Object getPerfil() { return perfil; }
        public String getTipo() { return tipo; }
        
        public Estudiante getEstudiante() {
            return perfil instanceof Estudiante ? (Estudiante) perfil : null;
        }
        
        public Docente getDocente() {
            return perfil instanceof Docente ? (Docente) perfil : null;
        }
    }
}