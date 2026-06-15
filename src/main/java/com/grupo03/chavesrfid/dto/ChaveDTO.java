package com.grupo03.chavesrfid.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChaveDTO {

    private Long id;

    @NotBlank
    private String nomeSala;

    @NotBlank
    private String status;
}

