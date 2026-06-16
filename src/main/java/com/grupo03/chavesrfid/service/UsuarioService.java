package com.grupo03.chavesrfid.service;

import com.grupo03.chavesrfid.model.Usuario;
import com.grupo03.chavesrfid.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public void salvar(Usuario usuario){
        usuarioRepository.save(usuario);
    }

    public void atualizar(String id, Usuario usuarioAtualizado){

        Optional<Usuario> usuarioOptional = usuarioRepository.findById(Long.valueOf(id));

        if(usuarioOptional.isEmpty()){
            // criar um exception handler e personalizar as exception depois
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuarioExistente = usuarioOptional.get();

        // Atualiza apenas campos presentes no request (não sobrescrever com null)
        if (usuarioAtualizado.getNome() != null) {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
        }
        if (usuarioAtualizado.getPerfil() != null) {
            usuarioExistente.setPerfil(usuarioAtualizado.getPerfil());
        }
        if (usuarioAtualizado.getAtivo() != null) {
            usuarioExistente.setAtivo(usuarioAtualizado.getAtivo());
        }
        if (usuarioAtualizado.getMatricula() != null) {
            usuarioExistente.setMatricula(usuarioAtualizado.getMatricula());
        }

        // uidRfid: só atualiza se não for null
        if (usuarioAtualizado.getUidRfid() != null) {
            usuarioExistente.setUidRfid(usuarioAtualizado.getUidRfid());
        }

        usuarioRepository.save(usuarioExistente);

    }

    public List<Usuario> listarTodos(){
        return usuarioRepository.findAll();
    }

    public Optional<Usuario> buscarPorId(String id){
        return usuarioRepository.findById(Long.valueOf(id));
    }

    public void desativar(String id){
        Usuario usuario = usuarioRepository.findById(Long.valueOf(id))
                .orElseThrow(() -> new RuntimeException("Usuário não existe"));


        usuario.setAtivo(false);
        usuarioRepository.save(usuario);
    }
}
