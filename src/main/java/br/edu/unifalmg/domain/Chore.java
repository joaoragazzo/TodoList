package br.edu.unifalmg.domain;


import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Chore {

    private String description;

    private Boolean isCompleted = false;

    private LocalDate deadline;

}
