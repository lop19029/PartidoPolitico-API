package gft.API.PartidoPoliticoAPI.dtos.politico;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroPoliticoDTO {
    private String nome;
    private String cpf;
    private EnderecoDTO endereco;
    private String telefone;
    private Long partidoId;
    private String cargo;
    private String foto;
}
