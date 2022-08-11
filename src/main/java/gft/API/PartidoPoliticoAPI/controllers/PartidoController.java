package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.partido.ConsultaPartidoDTO;
import gft.API.PartidoPoliticoAPI.dtos.partido.PartidoMapper;
import gft.API.PartidoPoliticoAPI.dtos.partido.RegistroPartidoDTO;
import gft.API.PartidoPoliticoAPI.entities.Partido;
import gft.API.PartidoPoliticoAPI.services.PartidoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/partidos")
public class PartidoController {

    private final PartidoService partidoService;

    public PartidoController(PartidoService partidoService) {
        this.partidoService = partidoService;
    }

    @PostMapping
    public ResponseEntity<ConsultaPartidoDTO> salvarPartido(@RequestBody RegistroPartidoDTO dto){
        Partido partido = partidoService.salvarPartido(PartidoMapper.fromDTO(dto));
        return ResponseEntity.ok(PartidoMapper.fromEntity(partido));
    }

    @GetMapping
    public ResponseEntity<Page<ConsultaPartidoDTO>> buscarTodosOsPartidos(@PageableDefault Pageable pageable){
        return ResponseEntity.ok(partidoService.listarTodosOsPartidos(pageable).map(PartidoMapper::fromEntity));
    }

    @GetMapping("{sigla}")
    public ResponseEntity<ConsultaPartidoDTO> buscarPartidoPorSigla(@PathVariable String sigla){
        Partido partido = partidoService.buscarPartidoPorSigla(sigla);
        return ResponseEntity.ok(PartidoMapper.fromEntity(partido));
    }

    @PutMapping("{sigla}")
    public ResponseEntity<ConsultaPartidoDTO> atualizarPartido(@PathVariable String sigla,
                                                               @RequestBody RegistroPartidoDTO dto){
        Partido partido = PartidoMapper.fromDTO(dto);
        Partido partidoSalvo = partidoService.atualizarPartido(partido, sigla);
        return ResponseEntity.ok(PartidoMapper.fromEntity(partidoSalvo));
    }

    @DeleteMapping("{sigla}")
    public ResponseEntity<String> excluirPartido(@PathVariable String sigla){
        partidoService.excluirPartido(sigla);
        return ResponseEntity.ok("O partido "+ sigla + " foi excluído da base de dados.");
    }
}
