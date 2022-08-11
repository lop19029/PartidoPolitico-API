package gft.API.PartidoPoliticoAPI.services;

import gft.API.PartidoPoliticoAPI.entities.Deputado;
import gft.API.PartidoPoliticoAPI.entities.Politico;
import gft.API.PartidoPoliticoAPI.exception.DataIntegrityException;
import gft.API.PartidoPoliticoAPI.exception.EntityNotFoundException;
import gft.API.PartidoPoliticoAPI.repositories.PoliticoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PoliticoService {

    private final PoliticoRepository politicoRepository;

    public PoliticoService(PoliticoRepository politicoRepository) {
        this.politicoRepository = politicoRepository;
    }

    public Politico salvarPolitico(Politico politico) {

        try{
            return politicoRepository.save(politico);
        }catch(Exception e)
        {
            e.printStackTrace();
            throw new DataIntegrityException("Os dados do politico são inválidos ou CPF está repetido.");
        }
    }

    public Page<Politico> listarTodosOsPoliticos(Pageable pageable) {
        return politicoRepository.findAll(pageable);
    }

    public Page<Politico> listarTodosOsPoliticosPorNomeAsc(Pageable pageable) {
        return politicoRepository.findByOrderByNomeAsc(pageable);
    }

    public Page<Politico> listarTodosOsPoliticosPorNomeDesc(Pageable pageable) {
        return politicoRepository.findByOrderByNomeDesc(pageable);
    }

    public Page<Politico> listarPoliticosPorCargo(String cargo, Pageable pageable){
        return politicoRepository.findPoliticoByCargo(cargo, pageable);
    }

    public Page<Politico> listarPoliticosPorNumeroDeProjetos(Integer numeroProjetos, Pageable pageable){
        return politicoRepository.findPoliticosByNumeroProjetos(numeroProjetos, pageable);
    }

    public Politico buscarPolitico(Long id){
        Optional<Politico> politico = politicoRepository.findById(id);
        return politico.orElseThrow(() -> new EntityNotFoundException("Politico não encontrado."));
    }

    public Politico buscarPoliticoPorIdECargo(Long id, String cargo){
        Optional<Politico> politico = politicoRepository.findByIdAndCargo(id, cargo);
        return politico.orElseThrow(() -> new EntityNotFoundException("Politico não encontrado."));
    }

    public void resetarLiderDePartido(Long partidoId){
        Optional<Politico> politico = politicoRepository.findPartidoLider(partidoId);
        if(politico.isPresent()){
            Deputado lider = (Deputado) politico.get();
            lider.setLiderOuRepresentante(false);
            this.salvarPolitico(lider);
        }
    }

    public Politico editarPolitico(Politico politico, Long id){
        Politico politicoOriginal = this.buscarPolitico(id);
        //Avoid overwrite politicos from other cargos
        if(!politico.getClass().equals(politicoOriginal.getClass())){
            throw new DataIntegrityException("O ID passado pertence a um politico com cargo diferente.");
        }
        politico.setId(politicoOriginal.getId());
        politico.setFoto(politicoOriginal.getFoto());
        return politicoRepository.save(politico);
    }

    public void excluirPolitico(Long id, String cargo){
        Politico politico = this.buscarPoliticoPorIdECargo(id, cargo);
        politicoRepository.delete(politico);
    }
}
