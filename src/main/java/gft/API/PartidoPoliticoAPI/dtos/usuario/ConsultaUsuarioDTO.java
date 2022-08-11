package gft.API.PartidoPoliticoAPI.dtos.usuario;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaUsuarioDTO {
    private Long id;
    private String username;
    private String nomePerfil;
}
