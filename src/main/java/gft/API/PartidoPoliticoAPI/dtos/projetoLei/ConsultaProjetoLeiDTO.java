package gft.API.PartidoPoliticoAPI.dtos.projetoLei;

import gft.API.PartidoPoliticoAPI.dtos.politico.ConsultaPoliticoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaProjetoLeiDTO {
    private Long id;
    private ConsultaPoliticoDTO politico;
    private String nome;
    private String descrição;
}
