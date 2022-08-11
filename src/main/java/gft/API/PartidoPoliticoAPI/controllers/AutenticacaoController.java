package gft.API.PartidoPoliticoAPI.controllers;


import gft.API.PartidoPoliticoAPI.dtos.auth.AutenticacaoDTO;
import gft.API.PartidoPoliticoAPI.dtos.auth.TokenDTO;
import gft.API.PartidoPoliticoAPI.services.AutenticacaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("v1/auth")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;

    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;
    }


    @PostMapping
    public ResponseEntity<TokenDTO> autenticar(@RequestBody AutenticacaoDTO autenticacaoDTO){

        try {
            return ResponseEntity.ok(autenticacaoService.autenticar(autenticacaoDTO));

        }catch (AuthenticationException ae) {
            ae.printStackTrace();
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
