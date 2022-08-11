package gft.API.PartidoPoliticoAPI.dtos;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class APIErrorDTO {
    private String message;
    private List<String> errors;
    private HttpStatus status;

    public APIErrorDTO() {
    }

    public APIErrorDTO(String message, List<String> errors, HttpStatus status) {
        this.message = message;
        this.errors = errors;
        this.status = status;
    }

    public APIErrorDTO(String message, String error, HttpStatus status) {
        this.message = message;
        this.errors = Arrays.asList(error);
        this.status = status;
    }
}
