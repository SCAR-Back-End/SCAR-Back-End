package com.grupo03.chavesrfid.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo03.chavesrfid.model.Log;
import com.grupo03.chavesrfid.repository.LogRepository;

@Service
public class LogService {

    @Autowired
    private LogRepository logRepository;

    public List<Log> listarTodos() {
        return logRepository.findAllByOrderByDataHoraDesc();
    }
}
