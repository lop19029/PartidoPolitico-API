package gft.API.PartidoPoliticoAPI.dtos.projetoLei;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProjetoLeiDTO {
    private Long politicoId;
    private String nome;
    private String descricao;
}
