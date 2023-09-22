package br.edu.unifalmg.services;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.expections.DuplicatedChoreExpection;
import br.edu.unifalmg.expections.InvalidDeadlineExpection;
import br.edu.unifalmg.expections.InvalidDescriptionExpection;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ChoreService {

    private List<Chore> chores;

    public ChoreService() {
        chores = new ArrayList<>();
    }



    public Chore addChore(String description, LocalDate deadline)
            throws InvalidDescriptionExpection, InvalidDeadlineExpection, DuplicatedChoreExpection {
        if (description == null || description.isEmpty() || description.isBlank()) {
            throw new InvalidDescriptionExpection("The description cannot be null or empty");
        }

        if (deadline == null || deadline.isBefore(LocalDate.now())) {
            throw new InvalidDeadlineExpection("The deadline cannot be null or before the current date");
        }



        for (Chore chore: chores) {
            if (Objects.equals(chore.getDescription(), description)
                    && chore.getDeadline().isEqual(LocalDate.now())) {
                throw new DuplicatedChoreExpection("The given chore already exists.");
            }
        }

        Chore chore = new Chore();
        chore.setIsCompleted(Boolean.FALSE);
        chore.setDescription(description);
        chore.setDeadline(deadline);

        this.chores.add(chore);

        return chore;
    }

}
