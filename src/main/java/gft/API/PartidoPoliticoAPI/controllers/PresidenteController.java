package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.dtos.presidente.PresidenteMapper;
import gft.API.PartidoPoliticoAPI.entities.Presidente;
import gft.API.PartidoPoliticoAPI.entities.Usuario;
import gft.API.PartidoPoliticoAPI.services.PartidoService;
import gft.API.PartidoPoliticoAPI.services.PoliticoService;
import gft.API.PartidoPoliticoAPI.services.UsuarioService;
import gft.API.PartidoPoliticoAPI.uploadImagens.FileUploadManager;
import org.apache.commons.io.FileUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("v1/presidentes")
public class PresidenteController {

    private final PoliticoService politicoService;
    private final PartidoService partidoService;
    private final UsuarioService usuarioService;

    public PresidenteController(PoliticoService politicoService, PartidoService partidoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Presidente> salvarPresidente(
            RegistroPoliticoDTO dto,
            @RequestParam(required = false) MultipartFile imagem) throws IOException {

        Presidente presidenteSalvo = (Presidente) politicoService.salvarPolitico(PresidenteMapper.fromDTO(dto));

        //Set partido
        presidenteSalvo.setPartido(partidoService
                .buscarPartido(Long.parseLong(String.valueOf(presidenteSalvo.getPartido().getId()))));

        //Upload foto
        //Set default if upload is empty and foto is empty
        //Do nothing if foto not empty and user didn't upload anything
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            presidenteSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + presidenteSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(presidenteSalvo);
        }
        else if(presidenteSalvo.getFoto().isEmpty()) {
            //Set default image
            presidenteSalvo.setFoto("default.png");
            politicoService.salvarPolitico(presidenteSalvo);
        }

        return ResponseEntity.ok(presidenteSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsPresidentes(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Presidente", pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Presidente", pageable).map(PoliticoMapper::fromEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarPresidentePorId(@PathVariable Long id){
        Presidente presidente = (Presidente) politicoService.buscarPoliticoPorIdECargo(id, "Presidente");

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(PoliticoMapper.fromEntityAdmin(presidente));
        }
        return ResponseEntity.ok(PoliticoMapper.fromEntity(presidente));
    }

    @PutMapping("{id}")
    public ResponseEntity<Presidente> editarPresidente(RegistroPoliticoDTO dto,@PathVariable Long id,
                                                   @RequestParam MultipartFile imagem) throws IOException {
        Presidente presidente = PresidenteMapper.fromDTO(dto);

        Presidente presidenteSalvo = (Presidente) politicoService.editarPolitico(presidente, id);

        //Check if imagem was edited
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            presidenteSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + presidenteSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(presidenteSalvo);
        }

        return ResponseEntity.ok(presidenteSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Presidente> excluirPresidente(@PathVariable Long id){
        politicoService.excluirPolitico(id, "Presidente");

        //delete politico pictures directory
        try {
            FileUtils.deleteDirectory(new File("C:/PartidoPoliticoAPI/politicos-fotos/" + id));
        } catch (IOException e) {
            System.out.println("Exception on foto delete: "+ e.getMessage());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.ok().build();
    }

}
