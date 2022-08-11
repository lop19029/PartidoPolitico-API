package gft.API.PartidoPoliticoAPI.dtos.processo;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.entities.Processo;

public class ProcessoMapper {
    public static Processo fromDTO(RegistroProcessoDTO dto){
        return new Processo(null, null, dto.getEmAndamento(), dto.getDescricao());
    }
    public static ConsultaProcessoDTO fromEntity(Processo en){
        return new ConsultaProcessoDTO(en.getId(), en.getPolitico().getId(),
                en.getEmAndamento(), en.getDescricao());
    }
}
