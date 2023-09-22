package br.edu.unifalmg.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class Chore {
    private String description;
    private Boolean isCompleted;
    private LocalDate deadline;

    public Chore() {

    }
}
