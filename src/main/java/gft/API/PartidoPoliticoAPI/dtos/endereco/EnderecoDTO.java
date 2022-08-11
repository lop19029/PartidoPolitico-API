package gft.API.PartidoPoliticoAPI.dtos.endereco;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EnderecoDTO {
    private String logradouro;
    private String numero;
    private String complemento;
    private String cep;
}
