package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.politico.PoliticoMapper;
import gft.API.PartidoPoliticoAPI.dtos.vereador.VereadorMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Vereador;
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
@RequestMapping("v1/vereadores")
public class VereadorController {

    private final PoliticoService politicoService;
    private final PartidoService partidoService;
    private final UsuarioService usuarioService;

    public VereadorController(PoliticoService politicoService, PartidoService partidoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Vereador> salvarVereador(
            RegistroPoliticoDTO dto,
            @RequestParam(required = false) MultipartFile imagem) throws IOException {

        Vereador vereadorSalvo = (Vereador) politicoService.salvarPolitico(VereadorMapper.fromDTO(dto));

        //Set partido
        vereadorSalvo.setPartido(partidoService
                .buscarPartido(Long.parseLong(String.valueOf(vereadorSalvo.getPartido().getId()))));

        //Upload foto
        //Set default if upload is empty and foto is empty
        //Do nothing if foto not empty and user didn't upload anything
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            vereadorSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + vereadorSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(vereadorSalvo);
        }
        else if(vereadorSalvo.getFoto().isEmpty()) {
            //Set default image
            vereadorSalvo.setFoto("default.png");
            politicoService.salvarPolitico(vereadorSalvo);
        }

        return ResponseEntity.ok(vereadorSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsVereadores(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();

        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Vereador", pageable).map(PoliticoMapper::fromEntityProcessavelAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Vereador", pageable).map(PoliticoMapper::fromEntityProcessavel));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> buscarVereadorPorId(@PathVariable Long id){
        Vereador vereador = (Vereador) politicoService.buscarPoliticoPorIdECargo(id, "Vereador");

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(PoliticoMapper.fromEntityProcessavelAdmin(vereador));
        }
        return ResponseEntity.ok(PoliticoMapper.fromEntityProcessavel(vereador));
    }

    @PutMapping("{id}")
    public ResponseEntity<Vereador> editarVereador(RegistroPoliticoDTO dto,@PathVariable Long id,
                                                   @RequestParam MultipartFile imagem) throws IOException {
        Vereador vereador = VereadorMapper.fromDTO(dto);

        Vereador vereadorSalvo = (Vereador) politicoService.editarPolitico(vereador, id);

        //Check if imagem was edited
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            vereadorSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + vereadorSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(vereadorSalvo);
        }

        return ResponseEntity.ok(vereadorSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Vereador> excluirVereador(@PathVariable Long id){
        politicoService.excluirPolitico(id, "Vereador");

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
