package com.grupo03.chavesrfid.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RfidScanResponseDTO {

    private String tipo;
    private String mensagem;
    private String usuario;

    public RfidScanResponseDTO(String tipo, String mensagem, String usuario) {
        this.tipo = tipo;
        this.mensagem = mensagem;
        this.usuario = usuario;
    }
}
