package gft.API.PartidoPoliticoAPI.services;

import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.exception.DataIntegrityException;
import gft.API.PartidoPoliticoAPI.exception.EntityNotFoundException;
import gft.API.PartidoPoliticoAPI.repositories.PartidoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.parser.Part;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PartidoService {

    private final PartidoRepository partidoRepository;

    public PartidoService(PartidoRepository partidoRepository) {
        this.partidoRepository = partidoRepository;
    }

    public Partido salvarPartido(Partido partido){
        try {
            return partidoRepository.save(partido);
        } catch(Exception e)
        {
            e.printStackTrace();
            throw new DataIntegrityException("Dados do partido inválidos ou repetidos.");
        }
    }

    public Page<Partido> listarTodosOsPartidos(Pageable pageable){
        return partidoRepository.findAll(pageable);
    }

    public Partido buscarPartido(Long id){
        return partidoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Partido não encontrado."));
    }

    public Partido buscarPartidoPorSigla(String sigla){
        Optional<Partido> partido = partidoRepository.findBySigla(sigla);
        return partido.orElseThrow(() -> new EntityNotFoundException("Partido não encontrado."));
    }

    public Partido atualizarPartido(Partido partido, String sigla){
        Partido partidoOriginal = this.buscarPartidoPorSigla(sigla);
        partido.setId(partidoOriginal.getId());
        return partidoRepository.save(partido);
    }

    public void excluirPartido(String sigla){
        Partido partido = buscarPartidoPorSigla(sigla);
        partidoRepository.delete(partido);
    }
}
