package gft.API.PartidoPoliticoAPI.dtos.usuario;

import gft.API.PartidoPoliticoAPI.entities.Perfil;
import gft.API.PartidoPoliticoAPI.entities.Usuario;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UsuarioMapper {
    public static Usuario fromDTO(RegistroUsuarioDTO dto){
        return new Usuario(null,dto.getUsername(),new BCryptPasswordEncoder().encode(dto.getSenha()),null);
    }

    public static ConsultaUsuarioDTO fromEntity(Usuario usuario){
        return new ConsultaUsuarioDTO(usuario.getId(), usuario.getUsername(), usuario.getPerfil().getNome());

    }
}
