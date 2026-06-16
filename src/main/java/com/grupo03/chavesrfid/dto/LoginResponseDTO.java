package com.grupo03.chavesrfid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponseDTO {
    private Long id;
    private String nome;
    private String matricula;
    private String perfil;
    private Boolean ativo;
}

