package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.projetoLei.ConsultaProjetoLeiDTO;
import gft.API.PartidoPoliticoAPI.dtos.projetoLei.ProjetoLeiMapper;
import gft.API.PartidoPoliticoAPI.dtos.projetoLei.RegistroProjetoLeiDTO;
import gft.API.PartidoPoliticoAPI.entities.Politico;
import gft.API.PartidoPoliticoAPI.entities.ProjetoLei;
import gft.API.PartidoPoliticoAPI.services.PoliticoService;
import gft.API.PartidoPoliticoAPI.services.ProjetoLeiService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/projetoslei")
public class ProjetoLeiController {

    private final ProjetoLeiService projetoLeiService;
    private final PoliticoService politicoService;

    public ProjetoLeiController(ProjetoLeiService projetoLeiService, PoliticoService politicoService) {
        this.projetoLeiService = projetoLeiService;
        this.politicoService = politicoService;
    }

    @PostMapping
    public ResponseEntity<ConsultaProjetoLeiDTO> salvarProjetoLei(@RequestBody RegistroProjetoLeiDTO dto){
        ProjetoLei projetoLei = ProjetoLeiMapper.fromDTO(dto);

        //Set politico
        Politico politico = politicoService.buscarPolitico(dto.getPoliticoId());
        projetoLei.setPolitico(politico);

        return ResponseEntity.ok(ProjetoLeiMapper.fromEntity(projetoLeiService.salvarProjetoLei(projetoLei)));
    }

    @GetMapping
    public ResponseEntity<Page<ConsultaProjetoLeiDTO>> buscarTodosOsProjetosLei(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(projetoLeiService.listarTodosOsProjetosLei(pageable).map(ProjetoLeiMapper::fromEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<ConsultaProjetoLeiDTO> buscarProjetoLei(@PathVariable Long id){
        ProjetoLei projetoLei = projetoLeiService.buscarProjetoLei(id);
        return ResponseEntity.ok(ProjetoLeiMapper.fromEntity(projetoLei));
    }

    @PutMapping("{id}")
    public ResponseEntity<ConsultaProjetoLeiDTO> atualizarProjetoLei(@PathVariable Long id,
                                                                     @RequestBody RegistroProjetoLeiDTO dto){
        ProjetoLei projetoLei = ProjetoLeiMapper.fromDTO(dto);

        //Set politico
        Politico politico = politicoService.buscarPolitico(dto.getPoliticoId());
        projetoLei.setPolitico(politico);

        ProjetoLei projetoLeiSalvo = projetoLeiService.atualizarProjetoLei(projetoLei, id);
        return ResponseEntity.ok(ProjetoLeiMapper.fromEntity(projetoLeiSalvo));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> excluirProjetoLei(@PathVariable Long id){
        projetoLeiService.excluirProjetoLei(id);
        return ResponseEntity.ok("O projeto de lei "+ id + " foi excluído da base de dados.");
    }
}
