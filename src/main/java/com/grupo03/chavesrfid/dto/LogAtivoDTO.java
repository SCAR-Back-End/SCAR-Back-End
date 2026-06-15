package com.grupo03.chavesrfid.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogAtivoDTO {

    private Long chaveId;
    private String nomeSala;
    private Long usuarioId;
    private String nomeUsuario;
    private String perfil;
    private LocalDateTime dataHora;
}

