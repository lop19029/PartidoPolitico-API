package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.processo.ConsultaProcessoDTO;
import gft.API.PartidoPoliticoAPI.dtos.processo.ProcessoMapper;
import gft.API.PartidoPoliticoAPI.dtos.processo.RegistroProcessoDTO;
import gft.API.PartidoPoliticoAPI.entities.Politico;
import gft.API.PartidoPoliticoAPI.entities.Processo;
import gft.API.PartidoPoliticoAPI.services.PoliticoService;
import gft.API.PartidoPoliticoAPI.services.ProcessoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/processos")
public class ProcessoController {

    private final ProcessoService processoService;
    private final PoliticoService politicoService;

    public ProcessoController(ProcessoService processoService, PoliticoService politicoService) {
        this.processoService = processoService;
        this.politicoService = politicoService;
    }

    @PostMapping
    public ResponseEntity<ConsultaProcessoDTO> salvarProcesso(@RequestBody RegistroProcessoDTO dto){
        Processo processo = ProcessoMapper.fromDTO(dto);

        //Set politico
        Politico politico = politicoService.buscarPolitico(dto.getPoliticoId());
        processo.setPolitico(politico);

        return ResponseEntity.ok(ProcessoMapper.fromEntity(processoService.salvarProcesso(processo)));
    }

    @GetMapping
    public ResponseEntity<Page<ConsultaProcessoDTO>> buscarTodosOsProcessos(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(processoService.listarTodosOsProcessos(pageable).map(ProcessoMapper::fromEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<ConsultaProcessoDTO> buscarProcesso(@PathVariable Long id){
        Processo processo = processoService.buscarProcesso(id);
        return ResponseEntity.ok(ProcessoMapper.fromEntity(processo));
    }

    @PutMapping("{id}")
    public ResponseEntity<ConsultaProcessoDTO> atualizarProcesso(@PathVariable Long id,
                                                                     @RequestBody RegistroProcessoDTO dto){
        Processo processo = ProcessoMapper.fromDTO(dto);

        //Set politico
        Politico politico = politicoService.buscarPolitico(dto.getPoliticoId());
        processo.setPolitico(politico);

        Processo processoSalvo = processoService.atualizarProcesso(processo, id);
        return ResponseEntity.ok(ProcessoMapper.fromEntity(processoSalvo));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> excluirProcesso(@PathVariable Long id){
        processoService.excluirProcesso(id);
        return ResponseEntity.ok("O processo "+ id + " foi excluído da base de dados.");
    }
}
