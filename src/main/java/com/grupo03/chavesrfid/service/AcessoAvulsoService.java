package com.grupo03.chavesrfid.service;

import com.grupo03.chavesrfid.dto.AcessoAvulsoResponseDTO;
import com.grupo03.chavesrfid.model.Chave;
import com.grupo03.chavesrfid.model.Log;
import com.grupo03.chavesrfid.model.Usuario;
import com.grupo03.chavesrfid.repository.ChaveRepository;
import com.grupo03.chavesrfid.repository.LogRepository;
import com.grupo03.chavesrfid.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AcessoAvulsoService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LogRepository logRepository;

    @Autowired
    private ChaveRepository chaveRepository;

    public AcessoAvulsoResponseDTO processar(Long usuarioId) {

        // a. Buscar usuário pelo id
        Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioId);
        if (usuarioOpt.isEmpty()) {
            return new AcessoAvulsoResponseDTO("NEGADO", "Usuário não encontrado", null);
        }
        Usuario usuario = usuarioOpt.get();

        // b. Verificar se o usuário está ativo
        if (!usuario.getAtivo()) {
            return new AcessoAvulsoResponseDTO("NEGADO", "Usuário inativo", usuario.getNome());
        }

        // c. Verificar se tem retirada pendente
        Optional<Log> retiradaPendente = logRepository.findRetiradaAtivaPorUsuario(usuario.getId());

        if (retiradaPendente.isPresent()) {
            // d. É uma DEVOLUÇÃO
            Log logAtivo = retiradaPendente.get();
            Chave chave = logAtivo.getChave();

            Log logDevolucao = new Log();
            logDevolucao.setUsuario(usuario);
            logDevolucao.setChave(chave);
            logDevolucao.setTipo("DEVOLUCAO");
            logDevolucao.setDataHora(LocalDateTime.now());
            logRepository.save(logDevolucao);

            chave.setStatus("DISPONIVEL");
            chaveRepository.save(chave);

            return new AcessoAvulsoResponseDTO(
                    "DEVOLUCAO",
                    "Devolução registrada com sucesso para " + chave.getNomeSala(),
                    usuario.getNome()
            );

        } else {
            // e. É uma RETIRADA
            List<Chave> disponiveis = chaveRepository.findByStatus("DISPONIVEL");
            if (disponiveis.isEmpty()) {
                return new AcessoAvulsoResponseDTO("NEGADO", "Nenhuma chave disponível", usuario.getNome());
            }

            Chave chave = disponiveis.get(0);

            Log logRetirada = new Log();
            logRetirada.setUsuario(usuario);
            logRetirada.setChave(chave);
            logRetirada.setTipo("RETIRADA");
            logRetirada.setDataHora(LocalDateTime.now());
            logRepository.save(logRetirada);

            chave.setStatus("EM_USO");
            chaveRepository.save(chave);

            return new AcessoAvulsoResponseDTO(
                    "RETIRADA",
                    "Retirada registrada com sucesso para " + chave.getNomeSala(),
                    usuario.getNome()
            );
        }
    }
}