import br.edu.unifalmg.domain.Chore;
import br.edu.unifalmg.emumerator.ChoreFilter;
import br.edu.unifalmg.exception.ChoreDontExistsException;
import br.edu.unifalmg.exception.DuplicatedChoreException;
import br.edu.unifalmg.exception.InvalidDeadlineException;
import br.edu.unifalmg.exception.InvalidDescriptionException;
import br.edu.unifalmg.services.ChoreService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ChoreServiceTest {

    @Test
    @DisplayName("#addChore > When the description is invalid > Throw Exception")
    void addChoreWhenTheDescriptionIsInvalidThrowException(){
        ChoreService service = new ChoreService();

        assertAll(
                () -> assertThrows(
                        InvalidDescriptionException.class,
                        () -> service.addChore(null, null)
                ),

                () -> assertThrows(
                        InvalidDescriptionException.class,
                        () -> service.addChore("", null)
                ),

                () -> assertThrows(
                        InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().plusDays(1))
                ),

                () -> assertThrows(
                        InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().plusDays(1))
                ),

                () -> assertThrows(
                        InvalidDescriptionException.class,
                        () -> service.addChore(null, LocalDate.now().minusDays(1))
                ),

                () -> assertThrows(
                        InvalidDescriptionException.class,
                        () -> service.addChore("", LocalDate.now().minusDays(1))
                )
        );
    }

    @Test
    @DisplayName("#addChore > When the deadline is invalid > Throw an exception")
    void addChoreWhenTheDeadlineIsInvalidThrowAnException() {
        ChoreService service = new ChoreService();

        assertAll(
                () -> assertThrows(InvalidDeadlineException.class,
                        () -> service.addChore("Description", null)
                ),

                () -> assertThrows(InvalidDeadlineException.class,
                    () -> service.addChore("Description", LocalDate.now().minusDays(1))
                )
        );
    }

    @Test
    @DisplayName("#addChore > When adding a chore > When the chore already exists > Throw an Exception")
    void addChoreWhenAddingAChoreWhenTheChoreAlreadyExistsThrowAnException() {
        ChoreService service = new ChoreService();
        service.addChore("Description", LocalDate.now());

        assertThrows(
                DuplicatedChoreException.class,
                () -> service.addChore("Description", LocalDate.now())
        );

    }

    @Test
    @DisplayName("#getChore > Get a chore when the chore exists > Return the Chore")
    void getChoreGetAChoreWhenTheChoreExistsReturnTheChore() {
        ChoreService service = new ChoreService();
        Chore chore = service.addChore("Description", LocalDate.now());

        assertEquals(
                chore, service.getChore("Description", LocalDate.now())
        );
    }

    @Test
    @DisplayName("#getChore > Get a chore when the chore does not exists > Return an Exception")
    void getChoreGetAChoreWhenTheChoreDoesNotExistsReturnAnException(){
        ChoreService service = new ChoreService();
        service.addChore("Description", LocalDate.now().plusDays(1));

        assertThrows(
                ChoreDontExistsException.class,
                () -> service.deleteChore("Description", LocalDate.now().plusDays(2))
        );
    }

    // TODO: Fazer os casos de teste para quando a descrição e o deadline é NULL (#GETCHORE)
    // O deadline é importante ser somente NULL para lançar exceção.


    @Test
    @DisplayName("#addChore > When the chore's list is empty > When adding a new chore > Add the chore")
    void addChoreWhenTheChoresListIsEmptyWhenAddingANewChoreAddTheChore() {
        ChoreService service = new ChoreService();
        Chore response = service.addChore("Description", LocalDate.now());
        assertAll(
                () -> assertEquals("Description", response.getDescription()),
                () -> assertEquals(LocalDate.now(), response.getDeadline()),
                () -> assertEquals(Boolean.FALSE, response.getIsCompleted())
        );
    }

    @Test
    @DisplayName("#addChore > When the chore's list has at least one element > When adding a new chore > Add the chore")
    void addChoreWhenTheChoresListHasAtLeastOneElementWhenAddingANewChoreAddTheChore() {
        ChoreService service = new ChoreService();
        service.addChore("Chore #01", LocalDate.now());
        service.addChore("Chore #02", LocalDate.now().plusDays(2));
        assertAll(
                () -> assertEquals(2, service.getChores().size()),
                () -> assertEquals("Chore #01", service.getChores().get(0).getDescription()),
                () -> assertEquals(LocalDate.now(), service.getChores().get(0).getDeadline()),
                () -> assertEquals(Boolean.FALSE, service.getChores().get(0).getIsCompleted()),
                () -> assertEquals("Chore #02", service.getChores().get(1).getDescription()),
                () -> assertEquals(LocalDate.now().plusDays(2), service.getChores().get(1).getDeadline()),
                () -> assertEquals(Boolean.FALSE, service.getChores().get(1).getIsCompleted())
        );
    }


    @Test
    @DisplayName("#removeChore > When the chore dont exists on the list > Throw an Exception")
    void removeChoreWhenTheChoreDontExistsOnTheListThrowAnException() {
        ChoreService service = new ChoreService();
        assertThrows(
          ChoreDontExistsException.class,
                () -> service.deleteChore("Chore #1", LocalDate.now())
        );
    }

    @Test
    @DisplayName("#removeChore > When the chore exists > Remove the chore")
    void removeChoreWhenTheChoreExistsRemoveTheChore() {
        ChoreService service = new ChoreService();

        service.addChore("Chore #1", LocalDate.now().plusDays(1));
        service.addChore("Chore #2", LocalDate.now().plusDays(2));

        service.deleteChore("Chore #1", LocalDate.now().plusDays(1));

        assertAll(
                () -> assertThrows(
                        ChoreDontExistsException.class,
                        () -> service.getChore("Chore #1", LocalDate.now().plusDays(1))
                    ),
                () -> assertDoesNotThrow(
                        () -> service.getChore("Chore #2", LocalDate.now().plusDays(2))
                    )
        );
    }

    @Test
    @DisplayName("#removeChore > When the deadline is invalid (null) > Return exception")
    void removeChoreWhenTheDeadlineIsInvalidReturnException(){
        //TODO

        ChoreService service = new ChoreService();

        service.addChore("Chore #1", LocalDate.now().plusDays(1));
    }

    // TODO: Fazer os casos de teste para quando a descrição e o deadline é NULL (#REMOVECHORE)
    // O deadline é importante ser somente NULL para lançar exceção.


    @Test
    @DisplayName("#toggleChore > When the chore have a valid date and exists > ToggleChore")
    void toggleChoreWhenTheChoreHaveAValidDateAndExistsToggleChore() {
        ChoreService service = new ChoreService();
        service.addChore("Chore #1", LocalDate.now().plusDays(1));

        assertAll(
                () -> assertDoesNotThrow(
                        () -> service.toggleChore("Chore #1", LocalDate.now().plusDays(1))
                        ),
                () -> assertTrue(service.getChore("Chore #1", LocalDate.now().plusDays(1)).getIsCompleted())

        );
    }

    @Test
    @DisplayName("#toggleChore > When the chore does not exists > Throw Exception")
    void toggleChoreWhenTheChoreDoesNotExistsThrowException() {
        ChoreService service = new ChoreService();

        assertThrows(
                ChoreDontExistsException.class,
                ()-> service.toggleChore("Chore #1", LocalDate.now().plusDays(1))
        );
    }

    @Test
    @DisplayName("#toggleChore > When the chore have an invalid description > Throw Exception")
    void toggleChoreWhenTheChoreHaveAnInvalidDescriptionThrowException() {
        ChoreService service = new ChoreService();

        assertThrows(
                InvalidDescriptionException.class,
                () -> service.toggleChore("", LocalDate.now().plusDays(1))
        );
    }


    @Test
    @DisplayName("#toggleChore > When the chore have an invalid deadline > Throw Exception")
    void toggleChoreWhenTheChoreHaveAnInvalidDeadlineThrowException() {
        ChoreService service = new ChoreService();

        assertAll(
                () -> assertThrows(InvalidDeadlineException.class,
                        () -> service.toggleChore("Chore #1", LocalDate.now().minusDays(1))),
                () -> assertThrows(InvalidDeadlineException.class,
                        () -> service.toggleChore("Chore #1", null))
        );
    }

    @Test
    @DisplayName("#filterChore > When the filter is ALL > When the list is empty > Return all chores")
    void filterChoreWhenTheFilterIsALLWhenTheListIsEmptyReturnAllChores() {
        ChoreService service = new ChoreService();
        List<Chore> response = service.filterChores(ChoreFilter.ALL);
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("#filterChore > When the filter is ALL > When the list is not empty > Return all chores")
    void filterChoreWhenTheFilterIsALLWhenTheListIsNotEmptyReturnAllChores() {
        ChoreService service = new ChoreService();
        service.addChore("Chore #01", LocalDate.now());
        service.addChore("Chore #02", LocalDate.now());
        service.toggleChore("Chore #02", LocalDate.now());

        List<Chore> response = service.filterChores(ChoreFilter.ALL);
        assertAll(
                () -> assertEquals(2, response.size()),
                () -> assertEquals("Chore #01", response.get(0).getDescription()),
                () -> assertEquals("Chore #02", response.get(1).getDescription())
        );
    }

    @Test
    @DisplayName("#filterChore > When the filter is COMPLETED > When the list is empty > Return only completed chores")
    void filterChoreWhenTheFilterIsCompletedWhenTheListIsEmptyReturnOnlyCompletedChores() {
        ChoreService service = new ChoreService();
        List<Chore> response = service.filterChores(ChoreFilter.COMPLETED);
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("#filterChore > When the filter is COMPLETED > When the list is not empty > Return only completed chores")
    void filterChoreWhenTheFilterIsCOMPLETEDWhenTheListIsNotEmptyReturnOnlyCompletedChores() {
        ChoreService service = new ChoreService();
        service.addChore("Chore #01", LocalDate.now());
        service.addChore("Chore #02", LocalDate.now());
        service.toggleChore("Chore #02", LocalDate.now());

        List<Chore> response = service.filterChores(ChoreFilter.COMPLETED);
        assertAll(
                () -> assertEquals(1, response.size()),
                () -> assertEquals("Chore #02", response.get(0).getDescription()),
                () -> assertEquals(Boolean.TRUE, response.get(0).getIsCompleted())
        );
    }

    @Test
    @DisplayName("#filterChroe > When the filter is UNCOMPLETED > When the list is empty > Return only uncompleted chores")
    void filterChoreWhenTheFilterIsUNCOMPLETEDWhenTheListIsEmptyReturnOnlyUncompletedChores() {
        ChoreService service = new ChoreService();
        List<Chore> response = service.filterChores(ChoreFilter.UNCOMPLETED);
        assertTrue(response.isEmpty());
    }

    @Test
    @DisplayName("#filterChroe > When the filter is UNCOMPLETED > When the list is not empty > Return only uncompleted chores")
    void filterChoreWhenTheFilterIsUNCOMPLETEDWhenTheListIsNotEmptyReturnOnlyUncompletedChores() {
        ChoreService service = new ChoreService();
        service.addChore("Chore #01", LocalDate.now());
        service.addChore("Chore #02", LocalDate.now());
        service.toggleChore("Chore #02", LocalDate.now());

        List<Chore> response = service.filterChores(ChoreFilter.UNCOMPLETED);
        assertAll(
                () -> assertEquals(1, response.size()),
                () -> assertEquals("Chore #01", response.get(0).getDescription()),
                () -> assertEquals(Boolean.FALSE, response.get(0).getIsCompleted())
        );
    }

    @Test
    @DisplayName("#printChores > When the list is empty > Print nothing")
    void printChoresWhenTheListIsEmptyPrintNothing() {
        ChoreService service = new ChoreService();
        assertEquals("", service.printChores());
    }

    @Test
    @DisplayName("#printChores > When the list is not empty > Print the chores")
    void printChoresWhenTheListIsNotEmptyPrintTheChores() {
        ChoreService service = new ChoreService();
        service.addChore("Chore #01", LocalDate.now().plusDays(1));
        service.addChore("Chore #02", LocalDate.now().plusDays(2));
        service.toggleChore("Chore #02", LocalDate.now().plusDays(2));

        String expectedOutput = "Descrição: Chore #01 Deadline: " + LocalDate.now().plusDays(1) + " Status: Incompleta\n" +
                                "Descrição: Chore #02 Deadline: " + LocalDate.now().plusDays(2) + " Status: Completa\n";

        assertEquals(expectedOutput, service.printChores());

    }

    @Test
    @DisplayName("#editChore > Editing only a description of a valid chore")
    void editChoreEditingADescriptionOfAValidChore() {
        ChoreService service = new ChoreService();

        service.addChore("Chore #01", LocalDate.now().plusDays(1));

        service.editChore("Chore #01", LocalDate.now().plusDays(1), "Chore Edited");

        assertDoesNotThrow(
                () -> service.getChore("Chore Edited", LocalDate.now().plusDays(1))
        );
    }

    @Test
    @DisplayName("#editChore > Editing only a deadline of a valid chore")
    void editChoreEditingADeadlineOfAValidChore() {
        ChoreService service = new ChoreService();

        service.addChore("Chore #01", LocalDate.now().plusDays(1));

        service.editChore("Chore #01", LocalDate.now().plusDays(1), LocalDate.now());

        assertDoesNotThrow(
                () -> service.getChore("Chore #01", LocalDate.now())
        );
    }

    @Test
    @DisplayName("#editChore > Editing the deadline and description of a valid chore")
    void editChoreEditingTheDeadlineAndDescriptionOfAValidChore() {
        ChoreService service = new ChoreService();

        service.addChore("Chore #01", LocalDate.now().plusDays(1));
        service.editChore("Chore #01", LocalDate.now().plusDays(1),
                "Chore Edited", LocalDate.now());

        assertDoesNotThrow(
                () -> service.getChore("Chore Edited", LocalDate.now())
        );
    }

}


