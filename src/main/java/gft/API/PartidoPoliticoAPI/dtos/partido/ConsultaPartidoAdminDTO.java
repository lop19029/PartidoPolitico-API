package gft.API.PartidoPoliticoAPI.dtos.partido;

import gft.API.PartidoPoliticoAPI.dtos.politico.ConsultaPoliticoAdminDTO;
import gft.API.PartidoPoliticoAPI.dtos.politico.ConsultaPoliticoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaPartidoAdminDTO {
    private Long id;
    private String nome;
    private String sigla;
    private List<ConsultaPoliticoAdminDTO> politicos;
}
