package gft.API.PartidoPoliticoAPI.dtos.partido;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Politico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PartidoMapper {

    public static Partido fromDTO(RegistroPartidoDTO dto){
        List<Politico> politicos = new ArrayList<>();
        return new Partido(null, dto.getNome(), dto.getSigla(),politicos);
    }

    public static ConsultaPartidoDTO fromEntity(Partido en){
        return new ConsultaPartidoDTO(en.getId(), en.getNome(), en.getSigla(),
                en.getPoliticos().stream().map(PoliticoMapper::fromEntity).collect(Collectors.toList()));
    }

    public static ConsultaPartidoAdminDTO fromEntityAdmin(Partido en){
        return new ConsultaPartidoAdminDTO(en.getId(), en.getNome(), en.getSigla(),
                en.getPoliticos().stream().map(PoliticoMapper::fromEntityAdmin).collect(Collectors.toList()));
    }
}
