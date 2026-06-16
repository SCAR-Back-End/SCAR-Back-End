package com.grupo03.chavesrfid.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.grupo03.chavesrfid.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

    // Busca o log de RETIRADA mais recente do usuário que ainda não possui DEVOLUCAO correspondente
    @Query("SELECT l FROM Log l WHERE l.usuario.id = :usuarioId AND l.tipo = 'RETIRADA' " +
           "AND NOT EXISTS (SELECT d FROM Log d WHERE d.usuario.id = :usuarioId " +
           "AND d.chave.id = l.chave.id AND d.tipo = 'DEVOLUCAO' AND d.dataHora > l.dataHora) " +
           "ORDER BY l.dataHora DESC LIMIT 1")
    Optional<Log> findRetiradaPendente(@Param("usuarioId") Long usuarioId);

    @Query("SELECT l FROM Log l WHERE l.tipo = 'RETIRADA' " +
           "AND NOT EXISTS (SELECT d FROM Log d WHERE d.chave.id = l.chave.id " +
           "AND d.tipo = 'DEVOLUCAO' AND d.dataHora > l.dataHora) " +
           "ORDER BY l.dataHora DESC")
    List<Log> findAllRetiradasPendentes();

    @Query("SELECT l FROM Log l WHERE l.tipo = 'RETIRADA' AND l.chave.id = :chaveId " +
            "AND NOT EXISTS (" +
            "  SELECT d FROM Log d WHERE d.tipo = 'DEVOLUCAO' " +
            "  AND d.chave.id = :chaveId AND d.dataHora > l.dataHora" +
            ") ORDER BY l.dataHora DESC")
    Optional<Log> findRetiradaAtivaPorChave(@Param("chaveId") Long chaveId);

    List<Log> findAllByOrderByDataHoraDesc();
}
