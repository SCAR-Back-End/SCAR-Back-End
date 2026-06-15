package com.grupo03.chavesrfid.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo03.chavesrfid.dto.LogAtivoDTO;
import com.grupo03.chavesrfid.model.Log;
import com.grupo03.chavesrfid.repository.LogRepository;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> listarTodos() {
        return logRepository.findAllByOrderByDataHoraDesc();
    }

    public List<LogAtivoDTO> listarAtivos() {
        return logRepository.findAllRetiradasPendentes().stream()
                .map(log -> new LogAtivoDTO(
                        log.getChave().getId(),
                        log.getChave().getNomeSala(),
                        log.getUsuario().getId(),
                        log.getUsuario().getNome(),
                        log.getUsuario().getPerfil(),
                        log.getDataHora()))
                .collect(Collectors.toList());
    }
}
