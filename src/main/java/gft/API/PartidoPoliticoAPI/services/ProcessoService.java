package gft.API.PartidoPoliticoAPI.services;

import gft.API.PartidoPoliticoAPI.entities.Processo;
import gft.API.PartidoPoliticoAPI.exception.DataIntegrityException;
import gft.API.PartidoPoliticoAPI.exception.EntityNotFoundException;
import gft.API.PartidoPoliticoAPI.repositories.ProcessoRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProcessoService {

    private final ProcessoRepository processoRepository;

    public ProcessoService(ProcessoRepository processoRepository) {
        this.processoRepository = processoRepository;
    }
    public Processo salvarProcesso(Processo processo){
        try {
            return processoRepository.save(processo);
        } catch(Exception e)
        {
            e.printStackTrace();
            throw new DataIntegrityException("Dados do processo inválidos ou repetidos.");
        }
    }

    public Page<Processo> listarTodosOsProcessos (Pageable pageable){
        return processoRepository.findAll(pageable);
    }

    public Processo buscarProcesso(Long id){
        return processoRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Processo não encontrado."));
    }

    public Processo atualizarProcesso(Processo processo, Long id){
        Processo processoOriginal = this.buscarProcesso(id);
        processo.setId(processoOriginal.getId());
        return processoRepository.save(processo);
    }

    public void excluirProcesso(Long id){
        Processo processo = this.buscarProcesso(id);
        processoRepository.delete(processo);
    }

}
