package com.grupo03.chavesrfid.service;

import com.grupo03.chavesrfid.dto.ChaveDTO;
import com.grupo03.chavesrfid.model.Chave;
import com.grupo03.chavesrfid.repository.ChaveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChaveService {

    @Autowired
    private ChaveRepository chaveRepository;

    public List<Chave> listarTodas() {
        return chaveRepository.findAll();
    }

    public Chave buscarPorId(Long id) {
        return chaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Chave não encontrada"));
    }

    public Chave criar(ChaveDTO dto) {
        Chave chave = new Chave();
        chave.setNomeSala(dto.getNomeSala());
        chave.setStatus(dto.getStatus());

        return chaveRepository.save(chave);
    }

    public Chave atualizar(Long id, ChaveDTO dto) {
        Chave chaveExistente = buscarPorId(id);
        chaveExistente.setNomeSala(dto.getNomeSala());
        chaveExistente.setStatus(dto.getStatus());

        return chaveRepository.save(chaveExistente);
    }

    public void remover(Long id) {
        chaveRepository.deleteById(id);
    }
}

