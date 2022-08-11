package gft.API.PartidoPoliticoAPI.dtos.processo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProcessoDTO {
    private Long politicoId;
    private Boolean emAndamento;
    private String descricao;
}
