package com.example.adbenturback.repository;

import com.example.adbenturback.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    
    Usuario findByUsername(String username);

    List<Usuario> findByIdUsuario(Long idUsuario);

    Usuario findByEmail(String email);

}
