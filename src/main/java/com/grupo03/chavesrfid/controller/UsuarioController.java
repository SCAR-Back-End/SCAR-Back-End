package com.grupo03.chavesrfid.controller;

import com.grupo03.chavesrfid.dto.UsuarioDTO;
import com.grupo03.chavesrfid.model.Usuario;
import com.grupo03.chavesrfid.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuario")
@CrossOrigin(origins = "*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody UsuarioDTO usuarioDTO){

        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId());
        usuario.setNome(usuarioDTO.getNome());
        usuario.setPerfil(usuarioDTO.getPerfil());
        usuario.setUidRfid(usuarioDTO.getUidRfid());
        usuario.setAtivo(true);

        usuarioService.salvar(usuario);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(usuario.getId())
                .toUri();


        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarTodos(){
        List<UsuarioDTO> usuarios = usuarioService.listarTodos().stream().map( usuario -> new UsuarioDTO(
                usuario.getId(),
                usuario.getNome(),
                usuario.getPerfil(),
                usuario.getUidRfid(),
                usuario.getAtivo())).toList();

        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("{id}")
    public ResponseEntity<UsuarioDTO> buscarPorId(@PathVariable("id") String id){

        Optional<Usuario> optionalUsuario = usuarioService.buscarPorId(id);

        if (optionalUsuario.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }
        Usuario usuario = optionalUsuario.get();

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setId(usuario.getId());
        usuarioDTO.setNome(usuario.getNome());
        usuarioDTO.setPerfil(usuario.getPerfil());
        usuarioDTO.setUidRfid(usuario.getUidRfid());
        usuarioDTO.setAtivo(usuario.getAtivo());

        return ResponseEntity.ok(usuarioDTO);
    }

    @PutMapping("{id}")
    public ResponseEntity<Void> atualizar(@PathVariable("id") String id, @RequestBody UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setNome(usuarioDTO.getNome());
        usuario.setPerfil(usuarioDTO.getPerfil());
        usuario.setUidRfid(usuarioDTO.getUidRfid());

        usuarioService.atualizar(id, usuario);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> desativar(@PathVariable("id") String id){
        usuarioService.desativar(id);

        return ResponseEntity.noContent().build();
    }
}
