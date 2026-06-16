package com.grupo03.chavesrfid.controller;

import com.grupo03.chavesrfid.dto.AcessoAvulsoRequestDTO;
import com.grupo03.chavesrfid.dto.AcessoAvulsoResponseDTO;
import com.grupo03.chavesrfid.service.AcessoAvulsoService;
import com.grupo03.chavesrfid.dto.TransferenciaRequestDTO;
import com.grupo03.chavesrfid.dto.TransferenciaResponseDTO;
import com.grupo03.chavesrfid.service.TransferenciaService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/movimentacao")
@CrossOrigin(origins = "*")
public class MovimentacaoController {

    @Autowired
    private TransferenciaService transferenciaService;


    @Autowired
    private AcessoAvulsoService acessoAvulsoService;

    @PostMapping("/transferencia")
    public ResponseEntity<?> transferir(@RequestBody @Valid TransferenciaRequestDTO dto) {
        try {
            TransferenciaResponseDTO response = transferenciaService.transferir(dto);
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    // Metodo adicionar na classe
    @PostMapping("/acesso-avulso")
    public ResponseEntity<?> acessoAvulso(@RequestBody @Valid AcessoAvulsoRequestDTO dto) {
        AcessoAvulsoResponseDTO response = acessoAvulsoService.processar(dto.getUsuarioId());
        if ("NEGADO".equals(response.getTipo())) {
            return ResponseEntity.status(403).body(response);
        }
        return ResponseEntity.ok(response);
    }
}