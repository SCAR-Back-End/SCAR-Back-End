package com.grupo03.chavesrfid.service;

import com.grupo03.chavesrfid.dto.TransferenciaRequestDTO;
import com.grupo03.chavesrfid.dto.TransferenciaResponseDTO;
import com.grupo03.chavesrfid.model.Chave;
import com.grupo03.chavesrfid.model.Log;
import com.grupo03.chavesrfid.model.Usuario;
import com.grupo03.chavesrfid.repository.ChaveRepository;
import com.grupo03.chavesrfid.repository.LogRepository;
import com.grupo03.chavesrfid.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TransferenciaService {

    @Autowired
    private ChaveRepository chaveRepository;

    @Autowired
    private LogRepository logRepository;
    @Autowired

    private UsuarioRepository usuarioRepository;

    public TransferenciaResponseDTO transferir(TransferenciaRequestDTO dto){

        // a. Buscar a chave
        Chave chave = chaveRepository.findById(dto.getChaveId())
                .orElseThrow(() -> new RuntimeException("Chave não encontrada"));

        // b. Verificar se está em uso
        if (!"EM_USO".equals(chave.getStatus())){
            throw new RuntimeException("Chave não está em uso no momento");
        }

        // c. Buscar retirada ativa para essa chave
        Log retiradaAtiva = logRepository.findRetiradaAtivaPorChave(chave.getId())
                .orElseThrow(() -> new RuntimeException("Nenhuma retirada ativa encontrada para essa chave"));

        // d. Guardar usuário de origem
        Usuario usuarioOrigem = retiradaAtiva.getUsuario();

        // e. Buscar usuário destino
        Usuario usuarioDestino = usuarioRepository.findById(dto.getUsuarioDestinoId())
                .orElseThrow(() -> new RuntimeException("Usuário destino não encontrado"));

        // f. Registrar devolução em nome do usuário de origem

        Log logDevolucao = new Log();
        logDevolucao.setUsuario(usuarioOrigem);
        logDevolucao.setChave(chave);
        logDevolucao.setTipo("DEVOLUCAO");
        logDevolucao.setDataHora(LocalDateTime.now());
        logRepository.save(logDevolucao);

        // g. Registrar retirada em nome do usuário destino
        Log logRetirada = new Log();
        logRetirada.setUsuario(usuarioDestino);
        logRetirada.setChave(chave);
        logRetirada.setTipo("RETIRADA");
        logRetirada.setDataHora(LocalDateTime.now());
        logRepository.save(logRetirada);

        // h. Chave permanece EM_USO — nenhuma alteração de status necessária

        // i. Retornar resposta
        return new TransferenciaResponseDTO(
                "Transferência realizada com sucesso",
                usuarioOrigem.getNome(),
                usuarioDestino.getNome(),
                chave.getNomeSala()
        );
    }
}
