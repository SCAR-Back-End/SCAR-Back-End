package com.grupo03.chavesrfid.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo03.chavesrfid.dto.LogAtivoDTO;
import com.grupo03.chavesrfid.model.Log;
import com.grupo03.chavesrfid.service.LogService;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "*")
public class LogController {

    @Autowired
    private LogService logService;

    @GetMapping
    public ResponseEntity<List<Log>> listarTodos() {
        return ResponseEntity.ok(logService.listarTodos());
    }

    @GetMapping("/ativos")
    public ResponseEntity<List<LogAtivoDTO>> listarAtivos() {
        return ResponseEntity.ok(logService.listarAtivos());
    }
}
