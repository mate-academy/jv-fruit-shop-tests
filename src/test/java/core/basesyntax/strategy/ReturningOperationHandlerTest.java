package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.Operation;
import org.junit.jupiter.api.Test;

class ReturningOperationHandlerTest {
    private final OperationHandler returning = new ReturningOperationHandler();
    private final FruitTransaction testTransaction = new FruitTransaction(
            Operation.RETURN, "banana", 100);
    private final FruitTransaction wrongTransaction = new FruitTransaction(
            Operation.RETURN, "banana", -183);

    @Test
    void returningOperationHandler_WrongValue_ThrowsException() {
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> returning.apply(wrongTransaction));
        assertEquals("Wrong transaction value: -183 is less than zero", exception.getMessage());
    }

    @Test
    void returningOperationHandler_OK_True() {
        Storage.dataBase.put("banana", 100);
        returning.apply(testTransaction);
        assertTrue(Storage.dataBase.containsKey("banana"));
        assertTrue(Storage.dataBase.containsValue(200));
    }

    @Test
    void returningOperationHandler_IsApplicableOK_True() {
        assertTrue(returning.isAplicable(testTransaction));
    }
}
