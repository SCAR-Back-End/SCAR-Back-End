package com.grupo03.chavesrfid.controller;

import com.grupo03.chavesrfid.dto.LoginRequestDTO;
import com.grupo03.chavesrfid.dto.LoginResponseDTO;
import com.grupo03.chavesrfid.model.Usuario;
import com.grupo03.chavesrfid.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDTO request) {
        
        // Validação de campos vazios
        if (request.getMatricula() == null || request.getMatricula().trim().isEmpty()) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Matrícula é obrigatória");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }

        if (request.getSenha() == null || request.getSenha().trim().isEmpty()) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Senha é obrigatória");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
        }

        // Buscar usuário pela matrícula
        Optional<Usuario> usuarioOptional = usuarioRepository.findByMatricula(request.getMatricula());

        if (usuarioOptional.isEmpty()) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Usuário não encontrado");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
        }

        Usuario usuario = usuarioOptional.get();

        // Validar se a senha é igual à matrícula (conforme solicitado)
        if (!usuario.getMatricula().equals(request.getSenha())) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Senha inválida");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(erro);
        }

        // Validar se o usuário está ativo
        if (!usuario.getAtivo()) {
            Map<String, String> erro = new HashMap<>();
            erro.put("erro", "Usuário inativo");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(erro);
        }


        // Retornar dados do usuário
        LoginResponseDTO response = new LoginResponseDTO(
            usuario.getId(),
            usuario.getNome(),
            usuario.getMatricula(),
            usuario.getPerfil(),
            usuario.getAtivo()
        );

        return ResponseEntity.ok(response);
    }
}

