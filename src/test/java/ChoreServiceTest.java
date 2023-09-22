import br.edu.unifalmg.expections.DuplicatedChoreExpection;
import br.edu.unifalmg.expections.InvalidDeadlineExpection;
import br.edu.unifalmg.expections.InvalidDescriptionExpection;
import br.edu.unifalmg.services.ChoreService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class ChoreServiceTest {

    @Test
    @DisplayName("#addChore > When the description is invalid > Throw Expection")
    void addChoreWhenTheDescriptionIsInvalidThrowException(){
        ChoreService service = new ChoreService();

        assertAll(
                () -> assertThrows(
                        InvalidDescriptionExpection.class,
                        () -> service.addChore(null, null)
                ),

                () -> assertThrows(
                        InvalidDescriptionExpection.class,
                        () -> service.addChore("", null)
                ),

                () -> assertThrows(
                        InvalidDescriptionExpection.class,
                        () -> service.addChore(null, LocalDate.now().plusDays(1))
                ),

                () -> assertThrows(
                        InvalidDescriptionExpection.class,
                        () -> service.addChore("", LocalDate.now().plusDays(1))
                ),

                () -> assertThrows(
                        InvalidDescriptionExpection.class,
                        () -> service.addChore(null, LocalDate.now().minusDays(1))
                ),

                () -> assertThrows(
                        InvalidDescriptionExpection.class,
                        () -> service.addChore("", LocalDate.now().minusDays(1))
                )
        );
    }

    @Test
    @DisplayName("#addChore > When the deadline is invalid > Throw an exception")
    void addChoreWhenTheDeadlineIsInvalidThrowAnException() {
        ChoreService service = new ChoreService();

        assertAll(
                () -> assertThrows(InvalidDeadlineExpection.class,
                        () -> service.addChore("Description", null)
                ),

                () -> assertThrows(InvalidDeadlineExpection.class,
                    () -> service.addChore("Description", LocalDate.now().minusDays(1))
                )
        );
    }

    @Test
    @DisplayName("#addChore > When adding a chore > When the chore already exists > Throw an expection")
    void addChoreWhenAddingAChoreWhenTheChoreAlreadyExistsThrowAnExpection() {
        ChoreService service = new ChoreService();
        service.addChore("Description", LocalDate.now());

        assertThrows(
                DuplicatedChoreExpection.class,
                () -> service.addChore("Description", LocalDate.now())
        );

    }



}
