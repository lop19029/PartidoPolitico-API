package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.entities.Usuario;
import gft.API.PartidoPoliticoAPI.services.PoliticoService;
import gft.API.PartidoPoliticoAPI.services.UsuarioService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/politicos")
public class PoliticoController {

    private final PoliticoService politicoService;
    private final UsuarioService usuarioService;

    public PoliticoController(PoliticoService politicoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.usuarioService = usuarioService;
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsPoliticos(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarTodosOsPoliticos(pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarTodosOsPoliticos(pageable).map(PoliticoMapper::fromEntity));

    }

    @GetMapping("/asc")
    public ResponseEntity<Page<?>> listarTodosOsPoliticosAsc(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarTodosOsPoliticosPorNomeAsc(pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarTodosOsPoliticosPorNomeAsc(pageable).map(PoliticoMapper::fromEntity));
    }

    @GetMapping("/desc")
    public ResponseEntity<Page<?>> listarTodosOsPoliticosDesc(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarTodosOsPoliticosPorNomeDesc(pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarTodosOsPoliticosPorNomeDesc(pageable).map(PoliticoMapper::fromEntity));
    }

    @GetMapping("{numeroProjetos}")
    public ResponseEntity<Page<?>> listarTodosOsPoliticosPorNumeroDeProjetos(@PathVariable Integer numeroProjetos,
                                                                             @PageableDefault Pageable pageable){
        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorNumeroDeProjetos(numeroProjetos,pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorNumeroDeProjetos(numeroProjetos,pageable).map(PoliticoMapper::fromEntity));

    }

}
