package gft.API.PartidoPoliticoAPI.dtos.senador;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Prefeito;
import gft.API.PartidoPoliticoAPI.entities.Senador;

import java.util.ArrayList;

public class SenadorMapper {
    public static Senador fromDTO(RegistroPoliticoDTO dto){
        return new Senador(null, dto.getNome(), dto.getCpf(), EnderecoMapper.fromDTO(dto.getEndereco()),
                dto.getTelefone(), new Partido(dto.getPartidoId()), dto.getFoto(),
                new ArrayList<>(), new ArrayList<>());
    }
}
