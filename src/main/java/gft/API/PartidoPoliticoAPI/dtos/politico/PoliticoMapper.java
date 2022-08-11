package gft.API.PartidoPoliticoAPI.dtos.politico;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.processo.ProcessoMapper;
import gft.API.PartidoPoliticoAPI.entities.Politico;

import java.util.stream.Collectors;

public class PoliticoMapper {
    public static ConsultaPoliticoDTO fromEntity(Politico politico){
        return new ConsultaPoliticoDTO(politico.getId(), politico.getNome(), politico.getClass().getSimpleName(),
                politico.getPartido(), politico.getProjetos().size(), politico.getFotoPath());
    }

    public static ConsultaPoliticoAdminDTO fromEntityAdmin (Politico politico){
        return new ConsultaPoliticoAdminDTO(politico.getId(), politico.getNome(), politico.getCpf(),
                EnderecoMapper.fromEntity(politico.getEndereco()), politico.getTelefone(),
                politico.getClass().getSimpleName(), politico.getPartido(), politico.getProjetos().size(),
                politico.getFotoPath());
    }

    public static ConsultaPoliticoProcessavelDTO fromEntityProcessavel(Politico politico){
        return new ConsultaPoliticoProcessavelDTO(politico.getId(), politico.getNome(), politico.getClass().getSimpleName(),
                politico.getPartido(), politico.getProjetos().size(), politico.getFotoPath(), politico.getProcessos().size());
    }

    public static ConsultaPoliticoProcessavelAdminDTO fromEntityProcessavelAdmin (Politico politico){
        return new ConsultaPoliticoProcessavelAdminDTO(politico.getId(), politico.getNome(), politico.getCpf(),
                EnderecoMapper.fromEntity(politico.getEndereco()), politico.getTelefone(),
                politico.getClass().getSimpleName(), politico.getPartido(), politico.getProjetos().size(),
                politico.getFotoPath(),
                politico.getProcessos().stream().map(ProcessoMapper::fromEntity).collect(Collectors.toList()));
    }
}
