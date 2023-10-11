package br.edu.unifalmg.exception;



import lombok.NoArgsConstructor;


@NoArgsConstructor
public class ChoreDontExistsException extends RuntimeException{
    public ChoreDontExistsException(String message) {
        super(message);
    }
}
