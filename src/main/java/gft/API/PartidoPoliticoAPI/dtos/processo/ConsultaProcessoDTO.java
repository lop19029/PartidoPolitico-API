package gft.API.PartidoPoliticoAPI.dtos.processo;

import gft.API.PartidoPoliticoAPI.dtos.politico.ConsultaPoliticoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaProcessoDTO {
    private Long id;
    private Long politicoId;
    private Boolean emAndamento;
    private String descrição;
}
