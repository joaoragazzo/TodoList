package br.edu.unifalmg.expections;

public class InvalidDescriptionExpection extends RuntimeException {
    public InvalidDescriptionExpection(String message) {
        super(message);
    }
}
