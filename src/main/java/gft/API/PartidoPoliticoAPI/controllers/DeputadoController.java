package gft.API.PartidoPoliticoAPI.controllers;

import gft.API.PartidoPoliticoAPI.dtos.deputado.DeputadoMapper;
import gft.API.PartidoPoliticoAPI.dtos.politico.RegistroPoliticoDTO;
import gft.API.PartidoPoliticoAPI.entities.Deputado;
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
@RequestMapping("v1/deputados")
public class DeputadoController {

    private final PoliticoService politicoService;
    private final PartidoService partidoService;
    private final UsuarioService usuarioService;

    public DeputadoController(PoliticoService politicoService, PartidoService partidoService, UsuarioService usuarioService) {
        this.politicoService = politicoService;
        this.partidoService = partidoService;
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<Deputado> salvarDeputado(
            RegistroPoliticoDTO dto,
            @RequestParam(required = false) MultipartFile imagem,
            @RequestParam Integer lider) throws IOException {

        Deputado deputadoSalvo = (Deputado) politicoService.salvarPolitico(DeputadoMapper.fromDTO(dto));

        //Set partido
        deputadoSalvo.setPartido(partidoService.buscarPartido(Long.parseLong(String.valueOf(deputadoSalvo.getPartido().getId()))));

        //Check if this is a liderOuRepresentante
        if(lider==1){
            politicoService.resetarLiderDePartido(deputadoSalvo.getPartido().getId());
            deputadoSalvo.setLiderOuRepresentante(true);
        }

        //Upload foto
        //Set default if upload is empty and foto is empty
        //Do nothing if foto not empty and user didn't upload anything
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            deputadoSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + deputadoSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(deputadoSalvo);
        }
        else if(deputadoSalvo.getFoto().isEmpty()) {
            //Set default image
            deputadoSalvo.setFoto("default.png");
            politicoService.salvarPolitico(deputadoSalvo);
        }

        return ResponseEntity.ok(deputadoSalvo);
    }

    @GetMapping
    public ResponseEntity<Page<?>> listarTodosOsDeputados(@PageableDefault Pageable pageable){

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Deputado", pageable).map(DeputadoMapper::fromEntityAdmin));
        }
        return ResponseEntity.ok(politicoService.listarPoliticosPorCargo("Deputado", pageable).map(DeputadoMapper::fromEntity));
    }


    @GetMapping("{id}")
    public ResponseEntity<?> buscarDeputadoPorId(@PathVariable Long id){
        Deputado deputado = (Deputado) politicoService.buscarPoliticoPorIdECargo(id, "Deputado");

        //Filter sensitive information
        Usuario usuario = usuarioService.obterUsuarioLogado();
        if (usuario.getPerfil().getNome().equals("ADMIN")){
            return ResponseEntity.ok(DeputadoMapper.fromEntityAdmin(deputado));
        }
        return ResponseEntity.ok(DeputadoMapper.fromEntity(deputado));
    }

    @PutMapping("{id}")
    public ResponseEntity<Deputado> editarDeputado(RegistroPoliticoDTO dto,@PathVariable Long id,
                                                   @RequestParam MultipartFile imagem,
                                                   @RequestParam Integer lider) throws IOException {
        Deputado deputado = DeputadoMapper.fromDTO(dto);

        //Check if this is a liderOuRepresentante
        if(lider==1){
            politicoService.resetarLiderDePartido(deputado.getPartido().getId());
            deputado.setLiderOuRepresentante(true);
        }

        Deputado deputadoSalvo = (Deputado) politicoService.editarPolitico(deputado, id);

        //Check if imagem was edited
        if (!(imagem.isEmpty())){
            //Save foto path
            String nomeDoArquivo = StringUtils.cleanPath(imagem.getOriginalFilename());
            deputadoSalvo.setFoto(nomeDoArquivo);

            //Save foto in directory
            String pastaDeFotos = "C:/PartidoPoliticoAPI/politicos-fotos/" + deputadoSalvo.getId();
            FileUploadManager.saveFile(pastaDeFotos, nomeDoArquivo, imagem);
            politicoService.salvarPolitico(deputadoSalvo);
        }

        return ResponseEntity.ok(deputadoSalvo);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Deputado> excluirDeputado(@PathVariable Long id){

        politicoService.excluirPolitico(id, "Deputado");

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
