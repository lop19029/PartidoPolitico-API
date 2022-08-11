package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.ministro.MinistroMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Ministro;
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
@RequestMapping("v1/ministros")
public class MinistroController {

    private final PoliticoService politicoService;
    private final PartidoService partidoService;
    private final UsuarioService usuarioService;

    public MinistroController(PoliticoService politicoService, PartidoService partidoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Ministro> salvarMinistro(
            RegistroPoliticoDTO dto,
            @RequestParam(required = false) MultipartFile imagem) throws IOException {

        Ministro ministroSalvo = (Ministro) politicoService.salvarPolitico(MinistroMapper.fromDTO(dto));

        //Set partido
        ministroSalvo.setPartido(partidoService.buscarPartido(Long.parseLong(String.valueOf(ministroSalvo.getPartido().getId()))));


        //Upload foto
        //Set default if upload is empty and foto is empty
        //Do nothing if foto not empty and user didn't upload anything
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            ministroSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + ministroSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(ministroSalvo);
        }
        else if(ministroSalvo.getFoto().isEmpty()) {
            //Set default image
            ministroSalvo.setFoto("default.png");
            politicoService.salvarPolitico(ministroSalvo);
        }

        return ResponseEntity.ok(ministroSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsMinistros(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Ministro", pageable).map(PoliticoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Ministro", pageable).map(PoliticoMapper::fromEntity));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarMinistroPorId(@PathVariable Long id){
        Ministro ministro = (Ministro) politicoService.buscarPoliticoPorIdECargo(id, "Ministro");

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(PoliticoMapper.fromEntityAdmin(ministro));
        }
        return ResponseEntity.ok(PoliticoMapper.fromEntity(ministro));
    }

    @PutMapping("{id}")
    public ResponseEntity<Ministro> editarMinistro(RegistroPoliticoDTO dto,@PathVariable Long id,
                                                   @RequestParam MultipartFile imagem) throws IOException {
        Ministro ministro = MinistroMapper.fromDTO(dto);

        Ministro ministroSalvo = (Ministro) politicoService.editarPolitico(ministro, id);

        //Check if imagem was edited
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            ministroSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + ministroSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(ministroSalvo);
        }

        return ResponseEntity.ok(ministroSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Ministro> excluirMinistro(@PathVariable Long id){
        politicoService.excluirPolitico(id, "Ministro");

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
