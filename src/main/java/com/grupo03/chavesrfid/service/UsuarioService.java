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

    public Usuario atualizar(String id, Usuario usuarioAtualizado){

        return usuarioRepository.findById(Long.valueOf(id)).map( usuarioExistente -> {
            usuarioExistente.setNome(usuarioAtualizado.getNome());
           usuarioExistente.setPerfil(usuarioAtualizado.getPerfil());
           usuarioExistente.setUidRfid(usuarioAtualizado.getUidRfid());

            return usuarioRepository.save(usuarioExistente);

            //Criar exception handler e personalizar a exception dps
        }).orElseThrow( () -> new RuntimeException("Usuário não existe"));

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
