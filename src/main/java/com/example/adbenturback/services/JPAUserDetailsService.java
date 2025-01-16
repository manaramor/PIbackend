package com.example.adbenturback.services;

import com.example.adbenturback.model.JPAUserDetails;
import com.example.adbenturback.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.example.adbenturback.repository.UsuarioRepository;


@Service
public class JPAUserDetailsService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Usuario user = usuarioRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Not found:" + username);
        }

        JPAUserDetails userDetails = new JPAUserDetails(user);
        return userDetails;
    }
}

