package br.edu.unifalmg.services;

import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.emumerator.ChoreFilter;
import br.edu.unifalmg.exception.ChoreDontExistsException;
import br.edu.unifalmg.exception.DuplicatedChoreException;
import br.edu.unifalmg.exception.InvalidDeadlineException;
import br.edu.unifalmg.exception.InvalidDescriptionException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChoreService {

    private List<Chore> chores;

    public ChoreService() {
        chores = new ArrayList<>();
    }

    public Chore addChore(String description, LocalDate deadline)
            throws InvalidDescriptionException, InvalidDeadlineException, DuplicatedChoreException {

        if (description == null || description.isEmpty() || description.isBlank()) {
            throw new InvalidDescriptionException("The description cannot be null or empty");
        }

        if (deadline == null || deadline.isBefore(LocalDate.now())) {
            throw new InvalidDeadlineException("The deadline cannot be null or before the current date");
        }

        for (Chore chore: chores) {
            if (Objects.equals(chore.getDescription(), description)
                    && chore.getDeadline().isEqual(LocalDate.now())) {
                throw new DuplicatedChoreException("The given chore already exists.");
            }
        }

        Chore chore = new Chore();
        chore.setIsCompleted(Boolean.FALSE);
        chore.setDescription(description);
        chore.setDeadline(deadline);

        this.chores.add(chore);

        return chore;
    }

    public List<Chore> getChores() {
        return this.chores;
    }

    public Chore getChore(String description, LocalDate deadline) {

        if (description == null || description.isEmpty() || description.isBlank()) {
            throw new InvalidDescriptionException("The description cannot be null or empty");
        }

        if (deadline == null) {
            throw new InvalidDeadlineException("The deadline cannot be null or before the current date");
        }

        for (Chore chore: chores) {
            if(chore.getDescription().equals(description) && chore.getDeadline().equals(deadline)) {
                return chore;
            }
        }

        throw new ChoreDontExistsException("The chore does not exists.");
    }

    public void deleteChore(String description, LocalDate deadline) throws ChoreDontExistsException {

        if (description == null || description.isEmpty() || description.isBlank()) {
            throw new InvalidDescriptionException("The description cannot be null or empty");
        }

        if (deadline == null) {
            throw new InvalidDeadlineException("The deadline cannot be null or before the current date");
        }

        Chore choreToDelete = null;

        for(Chore chore: chores) {
            if (chore.getDescription().equals(description) && chore.getDeadline().equals(deadline)) {
                choreToDelete = chore;
                break;
            }
        }
        if (choreToDelete != null) {
            this.chores.remove(choreToDelete);
        } else throw new ChoreDontExistsException("The chore does not exists.");
    }

    public void toggleChore(String description, LocalDate deadline) {

        if (description == null || description.isEmpty() || description.isBlank()) {
            throw new InvalidDescriptionException("The description cannot be null or empty");
        }

        if (deadline == null || deadline.isBefore(LocalDate.now())) {
            throw new InvalidDeadlineException("The deadline cannot be null or before the current date");
        }

        for(Chore chore: this.chores) {
            if(chore.getDescription().equals(description) && chore.getDeadline().equals(deadline)) {
                chore.setIsCompleted(!chore.getIsCompleted());
                return;
            }
        }

        throw new ChoreDontExistsException("The chore does not exists.");
    }

    public List<Chore> filterChores(ChoreFilter filter) {
        switch (filter) {
            case COMPLETED:
                return this.chores.stream().filter(Chore::getIsCompleted).collect(Collectors.toList());
            case UNCOMPLETED:
                return this.chores.stream().filter(chore -> !chore.getIsCompleted()).collect(Collectors.toList());
            default:
            case ALL:
                return this.chores;
        }
    }


    public String printChores() {
        String output = "";
        for(Chore chore : this.chores) {
            String status = "Incompleta";

            if (chore.getIsCompleted()) {
                status = "Completa";
            }

            output += "Descrição: " + chore.getDescription() +
                    " Deadline: " + chore.getDeadline() +
                    " Status: " + status + "\n";
        }

        return output;

    }

}
