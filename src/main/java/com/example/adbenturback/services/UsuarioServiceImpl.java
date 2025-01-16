package com.example.adbenturback.services;

import com.example.adbenturback.model.Usuario;
import com.example.adbenturback.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioServiceImpl implements UsuarioService{

    @Autowired
    UsuarioRepository usuarioRepo;

    @Override
    public List<Usuario> getAllUsuarios() {
        return usuarioRepo.findAll();
    }

    @Override
    public Usuario agregarUsuario(Usuario usuario) {
        if (usuario != null) {
            return usuarioRepo.save(usuario);
        }
        return null;
    }

    @Override
    public Usuario getUsuarioByUsername(String username) {
        return usuarioRepo.findByUsername(username);
    }

    @Override
    public Usuario getUsuarioById(Long id) {
        if (id != null) {
            return usuarioRepo.findById(id).orElse(null); 
        }
        return null; 
    }

    @Override
    public Usuario actualizarUsuario(Usuario usuario) {
        Usuario usuarioUpdated = null;
        if (usuario != null && usuario.getIdUsuario() != null) {
            usuarioUpdated = usuarioRepo.save(usuario);
        }
        return usuarioUpdated;
    }

    @Override
    public void borrarUsuario(Usuario usuario) {
        if (usuario != null && usuario.getIdUsuario() != null) {
            usuarioRepo.delete(usuario);
        }
    }

    @Override
    public Usuario getUsuarioByName(String nombre) {
        return null;
    }
}