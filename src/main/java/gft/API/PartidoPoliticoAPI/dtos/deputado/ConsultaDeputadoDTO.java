package gft.API.PartidoPoliticoAPI.dtos.deputado;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoDTO;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaDeputadoDTO {
    private Long id;
    private String nome;
    private String cargo;
    private Partido partido;
    private Integer numeroDeProjetos;
    private Integer numeroDeProcessos;
    private String foto;
    private boolean liderOuRepresentante;
}
