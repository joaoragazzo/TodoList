package br.edu.unifalmg.expections;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DuplicatedChoreExpection extends RuntimeException{
    public DuplicatedChoreExpection(String message) {
        super(message);
    }

}
