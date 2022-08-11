package gft.API.PartidoPoliticoAPI.dtos.auth;

import gft.API.PartidoPoliticoAPI.dtos.usuario.RegistroUsuarioDTO;

public class AutenticacaoMapper {

    public static AutenticacaoDTO fromUsuario(RegistroUsuarioDTO usuarioDTO){
        return new AutenticacaoDTO(usuarioDTO.getUsername(), usuarioDTO.getSenha());
    }
}
