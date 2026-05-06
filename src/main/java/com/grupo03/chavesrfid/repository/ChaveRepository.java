package com.grupo03.chavesrfid.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.grupo03.chavesrfid.model.Chave;

public interface ChaveRepository extends JpaRepository<Chave, Long> {

    List<Chave> findByStatus(String status);
}
