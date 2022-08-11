package gft.API.PartidoPoliticoAPI.dtos.ministro;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Ministro;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Prefeito;

import java.util.ArrayList;

public class MinistroMapper {
    public static Ministro fromDTO(RegistroPoliticoDTO dto){
        return new Ministro(null, dto.getNome(), dto.getCpf(), EnderecoMapper.fromDTO(dto.getEndereco()),
                dto.getTelefone(), new Partido(dto.getPartidoId()), dto.getFoto(),
                new ArrayList<>(), new ArrayList<>());
    }
}
