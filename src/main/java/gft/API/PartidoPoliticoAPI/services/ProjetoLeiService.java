package gft.API.PartidoPoliticoAPI.services;

import gft.API.PartidoPoliticoAPI.entities.ProjetoLei;
import gft.API.PartidoPoliticoAPI.exception.DataIntegrityException;
import gft.API.PartidoPoliticoAPI.exception.EntityNotFoundException;
import gft.API.PartidoPoliticoAPI.repositories.ProjetoLeiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProjetoLeiService {

    private final ProjetoLeiRepository projetoLeiRepository;

    public ProjetoLeiService(ProjetoLeiRepository projetoLeiRepository) {
        this.projetoLeiRepository = projetoLeiRepository;
    }
    public ProjetoLei salvarProjetoLei(ProjetoLei projetoLei){
        try {
            return projetoLeiRepository.save(projetoLei);
        } catch(Exception e)
        {
            e.printStackTrace();
            throw new DataIntegrityException("Dados do projeto inválidos ou repetidos.");
        }
    }

    public Page<ProjetoLei> listarTodosOsProjetosLei(Pageable pageable){
        return projetoLeiRepository.findAll(pageable);
    }

    public ProjetoLei buscarProjetoLei(Long id){
        return projetoLeiRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Projeto de Lei não encontrado."));
    }

    public ProjetoLei atualizarProjetoLei(ProjetoLei projetoLei, Long id){
        ProjetoLei projetoLeiOriginal = this.buscarProjetoLei(id);
        projetoLei.setId(projetoLeiOriginal.getId());
        return projetoLeiRepository.save(projetoLei);
    }

    public void excluirProjetoLei(Long id){
        ProjetoLei projetoLei = this.buscarProjetoLei(id);
        projetoLeiRepository.delete(projetoLei);
    }

}
