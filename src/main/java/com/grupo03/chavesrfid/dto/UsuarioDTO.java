package com.grupo03.chavesrfid.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String uidRfid;
    private String matricula;
    private String perfil;
    private Boolean ativo;

    public UsuarioDTO(Long id, String nome, String uidRfid, String matricula, String perfil, Boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.uidRfid = uidRfid;
        this.matricula = matricula;
        this.perfil = perfil;
        this.ativo = ativo;
    }
}
