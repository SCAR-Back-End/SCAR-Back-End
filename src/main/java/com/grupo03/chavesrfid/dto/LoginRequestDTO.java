package com.grupo03.chavesrfid.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {
    private String matricula;
    private String senha;
}

