package gft.API.PartidoPoliticoAPI.dtos.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AutenticacaoDTO {
    private String username;
    private String senha;
}