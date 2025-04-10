package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.infrastructure.db.Storage;
import core.basesyntax.service.FruitTransaction;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

public class OperationHandlerTest {
    @Test
    public void noSuchFruitNotOk() {
        Storage.STORAGE.remove("apple");
        OperationHandler pushareOperation = new ReturnOperation();
        RuntimeException exeption = assertThrows(NoSuchElementException.class,
                () -> pushareOperation.run(
                        new FruitTransaction(FruitTransaction.Operation.RETURN, "apple", 10)));

        assertEquals("Can't find fruit: apple", exeption.getMessage());
    }
}
