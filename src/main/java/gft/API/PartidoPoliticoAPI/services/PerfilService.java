package gft.API.PartidoPoliticoAPI.services;

import gft.API.PartidoPoliticoAPI.entities.Perfil;
import gft.API.PartidoPoliticoAPI.exception.EntityNotFoundException;
import gft.API.PartidoPoliticoAPI.repositories.PerfilRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PerfilService {
    private final PerfilRepository perfilRepository;

    public PerfilService(PerfilRepository perfilRepository) {
        this.perfilRepository = perfilRepository;
    }

    public Perfil buscarPerfil(Long id){
        Optional<Perfil> optional = perfilRepository.findById(id);
        return optional.orElseThrow(() -> new EntityNotFoundException("Perfil não encontrado"));
    }
}
