package br.edu.unifalmg.emumerator;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public enum ChoreFilter {
    ALL(1L, "All Chores"),
    COMPLETED(2L, "Only completed chores"),
    UNCOMPLETED(3L, "Only uncompleted chores");

    private long identifier;
    private String description;
}
