package gft.API.PartidoPoliticoAPI.dtos.politico;

import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Processo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaPoliticoProcessavelDTO {
    private Long id;
    private String nome;
    private String cargo;
    private Partido partido;
    private Integer numeroDeProjetos;
    private String fotoPath;
    private Integer numeroDeProcessos;
}
