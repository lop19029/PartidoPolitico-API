package gft.API.PartidoPoliticoAPI.exception;

public class PartidoAPIException extends RuntimeException{
    private String message;

    public PartidoAPIException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {this.message = message;}
}
