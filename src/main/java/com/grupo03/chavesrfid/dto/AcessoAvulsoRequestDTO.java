package com.grupo03.chavesrfid.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AcessoAvulsoRequestDTO {

    @NotNull
    private Long usuarioId;
}