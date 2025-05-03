package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String APPLE_KEY = "apple";
    private OperationHandler operationHandler;
    private FruitTransaction validData;
    private FruitTransaction invalidData;

    @BeforeEach
    void setUp() {
        operationHandler = new BalanceOperation();
        validData = new FruitTransaction(Operation.BALANCE, APPLE_KEY, 10);
        invalidData = new FruitTransaction(Operation.BALANCE, APPLE_KEY, -1);
    }

    @Test
    void operationProcess_validData_ok() {
        operationHandler.operationProcess(validData);
        assertEquals(10, Storage.fruitsStorage.get(APPLE_KEY));
    }

    @Test
    void operationProcess_negativeQuantity_notOk() {
        RuntimeException expectedMessage = assertThrows(RuntimeException.class,
                () -> operationHandler.operationProcess(invalidData));
        assertEquals("Quantity can't be less than 0", expectedMessage.getMessage());
    }
}
