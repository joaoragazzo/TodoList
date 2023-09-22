package br.edu.unifalmg.expections;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidDeadlineExpection extends RuntimeException {
    public InvalidDeadlineExpection(String message) {
        super(message);
    }

}
