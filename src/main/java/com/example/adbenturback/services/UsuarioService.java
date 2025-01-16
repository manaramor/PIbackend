package com.example.adbenturback.services;

import com.example.adbenturback.model.Usuario;

import java.util.List;

public interface UsuarioService {

    public List<Usuario> getAllUsuarios();

    public Usuario agregarUsuario(Usuario usuario);

    public Usuario getUsuarioByName(String nombre);

    public Usuario getUsuarioByUsername(String username);


    public Usuario getUsuarioById(Long id);

    public Usuario actualizarUsuario(Usuario usuario);

    public void borrarUsuario(Usuario usuario);
}
