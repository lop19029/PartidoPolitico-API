package gft.API.PartidoPoliticoAPI.services;

import gft.API.PartidoPoliticoAPI.entities.Usuario;
import gft.API.PartidoPoliticoAPI.exception.DataIntegrityException;
import gft.API.PartidoPoliticoAPI.exception.EntityNotFoundException;
import gft.API.PartidoPoliticoAPI.repositories.UsuarioRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario buscarUsuarioPorUsername(String username) {

        Optional<Usuario> optionalUsuario = usuarioRepository.findByUsername(username);

        if (optionalUsuario.isEmpty()){
            throw new UsernameNotFoundException("Usuario não encontrado");
        }

        return optionalUsuario.get();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return buscarUsuarioPorUsername(username);
    }

    public Usuario buscarUsuarioPorId(Long idUsuario) {
        Optional<Usuario> optional = usuarioRepository.findById(idUsuario);
        if (optional.isEmpty()){
            throw new EntityNotFoundException("Usuario não encontrado.");
        }
        return optional.get();
    }

    public Usuario salvarUsuario(Usuario usuario) {
        try {
            return usuarioRepository.save(usuario);
        } catch (Exception e){
            e.printStackTrace();
            throw new DataIntegrityException("Os dados do usuario são inválidos ou username está repetido.");
        }
    }

    public Usuario obterUsuarioLogado(){
        Usuario usuario = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return usuario;
    }
}
