package gft.API.PartidoPoliticoAPI.dtos.presidente;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Prefeito;
import gft.API.PartidoPoliticoAPI.entities.Presidente;

import java.util.ArrayList;

public class PresidenteMapper {
    public static Presidente fromDTO(RegistroPoliticoDTO dto){
        return new Presidente(null, dto.getNome(), dto.getCpf(), EnderecoMapper.fromDTO(dto.getEndereco()),
                dto.getTelefone(), new Partido(dto.getPartidoId()), dto.getFoto(),
                new ArrayList<>(), new ArrayList<>());
    }
}
