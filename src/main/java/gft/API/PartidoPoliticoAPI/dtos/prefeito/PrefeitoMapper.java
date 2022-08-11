package gft.API.PartidoPoliticoAPI.dtos.prefeito;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Deputado;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Prefeito;

import java.util.ArrayList;

public class PrefeitoMapper {
    public static Prefeito fromDTO(RegistroPoliticoDTO dto){
        return new Prefeito(null, dto.getNome(), dto.getCpf(), EnderecoMapper.fromDTO(dto.getEndereco()),
                dto.getTelefone(), new Partido(dto.getPartidoId()), dto.getFoto(),
                new ArrayList<>(), new ArrayList<>());
    }
}
