package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.dtos.prefeito.PrefeitoMapper;
import gft.API.PartidoPoliticoAPI.entities.Prefeito;
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
@RequestMapping("v1/prefeitos")
public class PrefeitoController {

    private final PoliticoService politicoService;
    private final PartidoService partidoService;
    private final UsuarioService usuarioService;

    public PrefeitoController(PoliticoService politicoService, PartidoService partidoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Prefeito> salvarPrefeito(
            RegistroPoliticoDTO dto,
            @RequestParam(required = false) MultipartFile imagem) throws IOException {

        Prefeito prefeitoSalvo = (Prefeito) politicoService.salvarPolitico(PrefeitoMapper.fromDTO(dto));

        //Set partido
        prefeitoSalvo.setPartido(partidoService
                .buscarPartido(Long.parseLong(String.valueOf(prefeitoSalvo.getPartido().getId()))));

        //Upload foto
        //Set default if upload is empty and foto is empty
        //Do nothing if foto not empty and user didn't upload anything
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            prefeitoSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + prefeitoSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(prefeitoSalvo);
        }
        else if(prefeitoSalvo.getFoto().isEmpty()) {
            //Set default image
            prefeitoSalvo.setFoto("default.png");
            politicoService.salvarPolitico(prefeitoSalvo);
        }

        return ResponseEntity.ok(prefeitoSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsPrefeitos(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Prefeito", pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Prefeito", pageable).map(PoliticoMapper::fromEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarPrefeitoPorId(@PathVariable Long id){
        Prefeito prefeito = (Prefeito) politicoService.buscarPoliticoPorIdECargo(id, "Prefeito");

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(PoliticoMapper.fromEntityAdmin(prefeito));
        }
        return ResponseEntity.ok(PoliticoMapper.fromEntity(prefeito));
    }

    @PutMapping("{id}")
    public ResponseEntity<Prefeito> editarPrefeito(RegistroPoliticoDTO dto,@PathVariable Long id,
                                                   @RequestParam MultipartFile imagem) throws IOException {
        Prefeito prefeito = PrefeitoMapper.fromDTO(dto);

        Prefeito prefeitoSalvo = (Prefeito) politicoService.editarPolitico(prefeito, id);

        //Check if imagem was edited
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            prefeitoSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + prefeitoSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(prefeitoSalvo);
        }

        return ResponseEntity.ok(prefeitoSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Prefeito> excluirPrefeito(@PathVariable Long id){
        politicoService.excluirPolitico(id, "Prefeito");

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
