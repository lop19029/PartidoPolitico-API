package gft.API.PartidoPoliticoAPI.dtos.governador;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Deputado;
import gft.API.PartidoPoliticoAPI.entities.Governador;
import gft.API.PartidoPoliticoAPI.entities.Partido;

import java.util.ArrayList;

public class GovernadorMapper {
    public static Governador fromDTO(RegistroPoliticoDTO dto){
        return new Governador(null, dto.getNome(), dto.getCpf(), EnderecoMapper.fromDTO(dto.getEndereco()),
                dto.getTelefone(), new Partido(dto.getPartidoId()), dto.getFoto(),
                new ArrayList<>(), new ArrayList<>());
    }
}
