package gft.API.PartidoPoliticoAPI.exception;

import gft.API.PartidoPoliticoAPI.dtos.APIErrorDTO;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({ PartidoAPIException.class })
    public ResponseEntity<APIErrorDTO> handlePartidoAPIException(PartidoAPIException ex, WebRequest request) {

        String error = "Erro no sistema";

        APIErrorDTO apiErrorDTO = new APIErrorDTO(ex.getMessage(), error, HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<APIErrorDTO>(apiErrorDTO, new HttpHeaders(),apiErrorDTO.getStatus());
    }

    @ExceptionHandler({ EntityNotFoundException.class })
    public ResponseEntity<APIErrorDTO> handleEntityNotFoundException(EntityNotFoundException ex, WebRequest request) {

        String error = "Recurso não encontrado";

        APIErrorDTO apiErrorDTO = new APIErrorDTO(ex.getMessage(), error, HttpStatus.NOT_FOUND);

        return new ResponseEntity<APIErrorDTO>(apiErrorDTO, new HttpHeaders(),apiErrorDTO.getStatus());
    }

    @ExceptionHandler({ DataIntegrityException.class })
    public ResponseEntity<APIErrorDTO> handleDataIntegrityException(DataIntegrityException ex, WebRequest request) {

        String error = "Não foi possivel inserir esses dados.";

        APIErrorDTO apiErrorDTO = new APIErrorDTO(ex.getMessage(), error, HttpStatus.CONFLICT);

        return new ResponseEntity<APIErrorDTO>(apiErrorDTO, new HttpHeaders(),apiErrorDTO.getStatus());
    }

}
