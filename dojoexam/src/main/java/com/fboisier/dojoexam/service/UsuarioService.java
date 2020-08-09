package com.fboisier.dojoexam.service;

import java.util.Optional;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.fboisier.dojoexam.entity.Usuario;
import com.fboisier.dojoexam.repositories.UsuarioRepository;

@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;

	//constructor
	public UsuarioService(UsuarioRepository usuarioRepository) {
		this.usuarioRepository = usuarioRepository;
	}

	//registrar usuario
	public Usuario registrarUsuario(Usuario usuario) {
		String hashed = BCrypt.hashpw(usuario.getPassword(), BCrypt.gensalt());
		usuario.setPassword(hashed);
		return usuarioRepository.save(usuario);
	}

    // encontrar un usuario por su email
    public Usuario findByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

	//encontrar usuario por ID
	public Usuario findUserById(Long id) {
		Optional<Usuario> u = usuarioRepository.findById(id);

		if (u.isPresent()) {
			return u.get();
		} else {
			return null;
		}
	}

	public boolean authenticateUsuario(String email, String password) {
		Usuario user = usuarioRepository.findByEmail(email);

		if (user == null) {
			return false;
		} else {
			if (BCrypt.checkpw(password, user.getPassword())) {
				return true;
			} else {
				return false;
			}
		}

	}
}
