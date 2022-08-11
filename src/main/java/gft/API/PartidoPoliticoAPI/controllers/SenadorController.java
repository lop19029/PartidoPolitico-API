package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.dtos.senador.SenadorMapper;
import gft.API.PartidoPoliticoAPI.entities.Senador;
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
@RequestMapping("v1/senadores")
public class SenadorController {

    private final PoliticoService politicoService;
    private final PartidoService partidoService;
    private final UsuarioService usuarioService;

    public SenadorController(PoliticoService politicoService, PartidoService partidoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Senador> salvarSenador(
            RegistroPoliticoDTO dto,
            @RequestParam(required = false) MultipartFile imagem) throws IOException {

        Senador senadorSalvo = (Senador) politicoService.salvarPolitico(SenadorMapper.fromDTO(dto));

        //Set partido
        senadorSalvo.setPartido(partidoService
                .buscarPartido(Long.parseLong(String.valueOf(senadorSalvo.getPartido().getId()))));

        //Upload foto

        //Set default if upload is empty and foto is empty
        //Do nothing if foto not empty and user didn't upload anything
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            senadorSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + senadorSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(senadorSalvo);
        }
        else if(senadorSalvo.getFoto().isEmpty()) {
            //Set default image
            senadorSalvo.setFoto("default.png");
            politicoService.salvarPolitico(senadorSalvo);
        }

        return ResponseEntity.ok(senadorSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsSenadores(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Senador", pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Senador", pageable).map(PoliticoMapper::fromEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarSenadorPorId(@PathVariable Long id){
        Senador senador = (Senador) politicoService.buscarPoliticoPorIdECargo(id, "Senador");

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(PoliticoMapper.fromEntityAdmin(senador));
        }
        return ResponseEntity.ok(PoliticoMapper.fromEntity(senador));
    }

    @PutMapping("{id}")
    public ResponseEntity<Senador> editarSenador(RegistroPoliticoDTO dto,@PathVariable Long id,
                                                   @RequestParam MultipartFile imagem) throws IOException {
        Senador senador = SenadorMapper.fromDTO(dto);

        Senador senadorSalvo = (Senador) politicoService.editarPolitico(senador, id);

        //Check if imagem was edited
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            senadorSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + senadorSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(senadorSalvo);
        }

        return ResponseEntity.ok(senadorSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Senador> excluirSenador(@PathVariable Long id){
        politicoService.excluirPolitico(id, "Senador");

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
