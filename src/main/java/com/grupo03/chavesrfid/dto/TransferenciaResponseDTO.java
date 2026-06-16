package com.grupo03.chavesrfid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TransferenciaResponseDTO {

    private String mensagem;
    private String nomeUsuarioOrigem;
    private String nomeUsuarioDestino;
    private String nomeSala;
}
