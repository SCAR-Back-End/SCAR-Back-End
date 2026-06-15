package com.grupo03.chavesrfid.controller;

import com.grupo03.chavesrfid.dto.ChaveDTO;
import com.grupo03.chavesrfid.model.Chave;
import com.grupo03.chavesrfid.service.ChaveService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/chave")
@CrossOrigin(origins = "*")
public class ChaveController {

    @Autowired
    private ChaveService chaveService;

    @GetMapping
    public ResponseEntity<List<Chave>> listarTodas() {
        return ResponseEntity.ok(chaveService.listarTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chave> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(chaveService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<Chave> criar(@RequestBody @Valid ChaveDTO chaveDTO) {
        Chave chaveCriada = chaveService.criar(chaveDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(chaveCriada);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chave> atualizar(@PathVariable Long id, @RequestBody @Valid ChaveDTO chaveDTO) {
        Chave chaveAtualizada = chaveService.atualizar(id, chaveDTO);
        return ResponseEntity.ok(chaveAtualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        chaveService.remover(id);
        return ResponseEntity.noContent().build();
    }
}

