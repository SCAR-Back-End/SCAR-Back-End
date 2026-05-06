package com.grupo03.chavesrfid.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo03.chavesrfid.dto.RfidScanResponseDTO;
import com.grupo03.chavesrfid.model.Chave;
import com.grupo03.chavesrfid.model.Log;
import com.grupo03.chavesrfid.model.Usuario;
import com.grupo03.chavesrfid.repository.ChaveRepository;
import com.grupo03.chavesrfid.repository.LogRepository;
import com.grupo03.chavesrfid.repository.UsuarioRepository;

@Service
public class RfidService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ChaveRepository chaveRepository;

    @Autowired
    private LogRepository logRepository;

    public RfidScanResponseDTO processar(String uid) {
        // 1. Buscar usuário pelo UID
        Optional<Usuario> optUsuario = usuarioRepository.findByUidRfid(uid);
        if (optUsuario.isEmpty()) {
            return new RfidScanResponseDTO("NEGADO", "Usuário não encontrado", null);
        }

        Usuario usuario = optUsuario.get();

        // 2. Verificar se existe retirada pendente
        Optional<Log> optRetirada = logRepository.findRetiradaPendente(usuario.getId());

        if (optRetirada.isPresent()) {
            // 3. É uma DEVOLUÇÃO
            Log retirada = optRetirada.get();
            Chave chave = retirada.getChave();

            Log devolucao = new Log();
            devolucao.setUsuario(usuario);
            devolucao.setChave(chave);
            devolucao.setTipo("DEVOLUCAO");
            devolucao.setDataHora(LocalDateTime.now());
            logRepository.save(devolucao);

            chave.setStatus("DISPONIVEL");
            chaveRepository.save(chave);

            return new RfidScanResponseDTO("DEVOLUCAO",
                    "Chave da sala " + chave.getNomeSala() + " devolvida com sucesso",
                    usuario.getNome());
        } else {
            // 4. É uma RETIRADA
            List<Chave> disponiveis = chaveRepository.findByStatus("DISPONIVEL");
            if (disponiveis.isEmpty()) {
                return new RfidScanResponseDTO("NEGADO", "Nenhuma chave disponível", usuario.getNome());
            }

            Chave chave = disponiveis.get(0);

            Log retirada = new Log();
            retirada.setUsuario(usuario);
            retirada.setChave(chave);
            retirada.setTipo("RETIRADA");
            retirada.setDataHora(LocalDateTime.now());
            logRepository.save(retirada);

            chave.setStatus("EM_USO");
            chaveRepository.save(chave);

            return new RfidScanResponseDTO("RETIRADA",
                    "Chave da sala " + chave.getNomeSala() + " retirada com sucesso",
                    usuario.getNome());
        }
    }
}
