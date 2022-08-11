package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.deputado.DeputadoMapper;
import gft.API.PartidoPoliticoAPI.dtos.governador.GovernadorMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Governador;
import gft.API.PartidoPoliticoAPI.entities.Politico;
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
@RequestMapping("v1/governadores")
public class GovernadorController {

    private final PoliticoService politicoService;
    private final PartidoService partidoService;
    private final UsuarioService usuarioService;

    public GovernadorController(PoliticoService politicoService, PartidoService partidoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Governador> salvarGovernador(
            RegistroPoliticoDTO dto,
            @RequestParam(required = false) MultipartFile imagem) throws IOException {

        Governador governadorSalvo = (Governador) politicoService.salvarPolitico(GovernadorMapper.fromDTO(dto));

        //Set partido
        governadorSalvo.setPartido(partidoService.buscarPartido(Long.parseLong(String.valueOf(governadorSalvo.getPartido().getId()))));


        //Upload foto
        //Set default if upload is empty and foto is empty
        //Do nothing if foto not empty and user didn't upload anything
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            governadorSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + governadorSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(governadorSalvo);
        }
        else if(governadorSalvo.getFoto().isEmpty()) {
            //Set default image
            governadorSalvo.setFoto("default.png");
            politicoService.salvarPolitico(governadorSalvo);
        }

        return ResponseEntity.ok(governadorSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsGovernadores(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Governador", pageable).map(PoliticoMapper::fromEntityProcessavelAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Governador", pageable).map(PoliticoMapper::fromEntityProcessavel));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarGovernadorPorId(@PathVariable Long id){
        Governador governador = (Governador) politicoService.buscarPoliticoPorIdECargo(id, "Governador");

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(PoliticoMapper.fromEntityProcessavelAdmin(governador));
        }
        return ResponseEntity.ok(PoliticoMapper.fromEntityProcessavel(governador));
    }

    @PutMapping("{id}")
    public ResponseEntity<Governador> editarGovernador(RegistroPoliticoDTO dto,@PathVariable Long id,
                                                   @RequestParam MultipartFile imagem) throws IOException {
        Governador governador = GovernadorMapper.fromDTO(dto);

        Governador governadorSalvo = (Governador) politicoService.editarPolitico(governador, id);

        //Check if imagem was edited
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            governadorSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + governadorSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(governadorSalvo);
        }

        return ResponseEntity.ok(governadorSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Governador> excluirGovernador(@PathVariable Long id){
        politicoService.excluirPolitico(id, "Governador");

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
