package com.grupo03.chavesrfid.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RfidScanRequestDTO {

    @NotBlank(message = "O UID do crachá é obrigatório")
    private String uid;
}
