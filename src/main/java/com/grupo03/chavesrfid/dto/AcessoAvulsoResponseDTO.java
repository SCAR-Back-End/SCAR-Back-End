package com.grupo03.chavesrfid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AcessoAvulsoResponseDTO {

    private String tipo;
    private String mensagem;
    private String usuario;
}