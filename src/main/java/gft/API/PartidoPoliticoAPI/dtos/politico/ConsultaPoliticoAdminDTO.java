package gft.API.PartidoPoliticoAPI.dtos.politico;

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
public class ConsultaPoliticoAdminDTO {
    private Long id;
    private String nome;
    private String cpf;
    private EnderecoDTO endereco;
    private String telefone;
    private String cargo;
    private Partido partido;
    private Integer numeroDeProjetos;
    private String fotoPath;
}
