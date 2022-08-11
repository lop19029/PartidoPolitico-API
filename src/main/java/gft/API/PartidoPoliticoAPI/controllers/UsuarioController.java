package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.auth.AutenticacaoMapper;
import gft.API.PartidoPoliticoAPI.dtos.auth.TokenDTO;
import gft.API.PartidoPoliticoAPI.dtos.usuario.RegistroUsuarioDTO;
import gft.API.PartidoPoliticoAPI.dtos.usuario.UsuarioMapper;
import gft.API.PartidoPoliticoAPI.entities.Usuario;
import gft.API.PartidoPoliticoAPI.services.AutenticacaoService;
import gft.API.PartidoPoliticoAPI.services.PerfilService;
import gft.API.PartidoPoliticoAPI.services.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("v1/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AutenticacaoService autenticacaoService;
    private final PerfilService perfilService;

    public UsuarioController(UsuarioService usuarioService, AutenticacaoService autenticacaoService, PerfilService perfilService) {
        this.usuarioService = usuarioService;
        this.autenticacaoService = autenticacaoService;
        this.perfilService = perfilService;
    }

    @ApiOperation(value = "Registra um novo usuario no sistema e gera um token de autenticação.")
    @PostMapping
    public ResponseEntity<TokenDTO> salvarUsuario(@RequestBody RegistroUsuarioDTO dto) throws AuthenticationException {
        Usuario usuario = usuarioService.salvarUsuario(UsuarioMapper.fromDTO(dto));
        usuario.setPerfil(perfilService.buscarPerfil(dto.getPerfilId()));
        usuarioService.salvarUsuario(usuario);

        //Autenticate new user
        TokenDTO token = autenticacaoService.autenticar(AutenticacaoMapper.fromUsuario(dto));

        return ResponseEntity.ok(token);
    }
}
