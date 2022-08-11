package gft.API.PartidoPoliticoAPI.dtos.vereador;

import gft.API.PartidoPoliticoAPI.dtos.endereco.EnderecoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Deputado;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.entities.Vereador;

import java.util.ArrayList;

public class VereadorMapper {
    public static Vereador fromDTO(RegistroPoliticoDTO dto){
        return new Vereador(null, dto.getNome(), dto.getCpf(), EnderecoMapper.fromDTO(dto.getEndereco()),
                dto.getTelefone(), new Partido(dto.getPartidoId()), dto.getFoto(),
                new ArrayList<>(), new ArrayList<>());
    }
}
