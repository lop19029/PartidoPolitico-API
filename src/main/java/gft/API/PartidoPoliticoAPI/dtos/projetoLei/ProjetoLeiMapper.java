package gft.API.PartidoPoliticoAPI.dtos.projetoLei;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.entities.ProjetoLei;

public class ProjetoLeiMapper {
    public static ProjetoLei fromDTO(RegistroProjetoLeiDTO dto){
        return new ProjetoLei(null, null, dto.getNome(), dto.getDescricao());
    }
    public static ConsultaProjetoLeiDTO fromEntity(ProjetoLei en){
        return new ConsultaProjetoLeiDTO(en.getId(), PoliticoMapper.fromEntity(en.getPolitico()),
                en.getNome(), en.getDescricao());
    }
}
