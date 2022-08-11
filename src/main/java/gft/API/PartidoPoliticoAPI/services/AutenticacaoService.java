package gft.API.PartidoPoliticoAPI.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import gft.API.PartidoPoliticoAPI.dtos.auth.AutenticacaoDTO;
import gft.API.PartidoPoliticoAPI.dtos.auth.TokenDTO;
import gft.API.PartidoPoliticoAPI.entities.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Date;

@Service
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;


    @Value("${partidos.jwt.expiration}")
    private String expiration;

    @Value("${partidos.jwt.secret}")
    private String secret;

    @Value("${partidos.jwt.issuer}")
    private String issuer;

    public AutenticacaoService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public TokenDTO autenticar(AutenticacaoDTO autenticacaoDTO) throws AuthenticationException {

        Authentication authenticate = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(autenticacaoDTO.getUsername(), autenticacaoDTO.getSenha()));

        String token = gerarToken(authenticate);

        return new TokenDTO(token);
    }

    private Algorithm criarAlgoritmo(){
        return Algorithm.HMAC256(secret);
    }

    private String gerarToken(Authentication authenticate) {
        Usuario principal = (Usuario)authenticate.getPrincipal();
        Date hoje = new Date();
        Date dataExpiracao = new Date(hoje.getTime() + Long.parseLong(expiration));

        return JWT.create()
                .withIssuer(issuer)
                .withExpiresAt(dataExpiracao)
                .withSubject(principal.getId().toString())
                .sign(this.criarAlgoritmo());
    }

    public boolean verificaToken(String token) {

        try{
            if(token==null)
                return false;
            JWT.require(this.criarAlgoritmo()).withIssuer(issuer).build().verify(token);
            return true;

        } catch (JWTVerificationException exception) {

            return false;
        }
    }

    public Long retornarIdUsuario(String token) {
        //User ID as String
        String subject = JWT.require(this.criarAlgoritmo()).withIssuer(issuer).build().verify(token).getSubject();
        return Long.parseLong(subject); //Returns user ID as Long
    }

}
