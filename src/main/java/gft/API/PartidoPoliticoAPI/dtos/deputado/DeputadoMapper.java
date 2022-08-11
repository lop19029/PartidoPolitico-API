package gft.API.PartidoPoliticoAPI.dtos.deputado;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.ConsultaPoliticoAdminDTO;
import gft.API.PartidoPoliticoAPI.dtos.politico.ConsultaPoliticoDTO;
import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.dtos.processo.ProcessoMapper;
import gft.API.PartidoPoliticoAPI.entities.Deputado;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Politico;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class DeputadoMapper {
    public static Deputado fromDTO(RegistroPoliticoDTO dto){
        return new Deputado(null, dto.getNome(), dto.getCpf(), EnderecoMapper.fromDTO(dto.getEndereco()),
                dto.getTelefone(), new Partido(dto.getPartidoId()), dto.getFoto(),
                new ArrayList<>(), new ArrayList<>(), false);
    }
    public static ConsultaDeputadoDTO fromEntity(Politico politico){
        Deputado deputado = (Deputado) politico;
        return new ConsultaDeputadoDTO(deputado.getId(), deputado.getNome(), deputado.getClass().getSimpleName(),
                deputado.getPartido(), deputado.getProjetos().size(), deputado.getProcessos().size(), deputado.getFotoPath(), deputado.getLiderOuRepresentante());
    }

    public static ConsultaDeputadoAdminDTO fromEntityAdmin (Politico politico){
        Deputado deputado = (Deputado) politico;
        return new ConsultaDeputadoAdminDTO(deputado.getId(), deputado.getNome(), deputado.getCpf(),
                EnderecoMapper.fromEntity(deputado.getEndereco()), deputado.getTelefone(),
                deputado.getClass().getSimpleName(), deputado.getPartido(), deputado.getProjetos().size(),
                deputado.getProcessos().stream().map(ProcessoMapper::fromEntity).collect(Collectors.toList()),
                deputado.getFotoPath(), deputado.getLiderOuRepresentante());
    }
}
