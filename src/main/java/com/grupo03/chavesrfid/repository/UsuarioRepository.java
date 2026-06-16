package com.grupo03.chavesrfid.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo03.chavesrfid.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByUidRfid(String uidRfid);

    Optional<Usuario> findByMatricula(String matricula);
}
