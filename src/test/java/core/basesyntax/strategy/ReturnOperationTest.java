package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private static final String APPLE_KEY = "apple";
    private OperationHandler operationHandler;
    private FruitTransaction validData;
    private FruitTransaction dataLessZero;

    @BeforeEach
    void setUp() {
        operationHandler = new ReturnOperation();
        validData = new FruitTransaction(Operation.RETURN, APPLE_KEY, 10);
        dataLessZero = new FruitTransaction(Operation.RETURN, APPLE_KEY, -2);
        Storage.fruitsStorage.clear();
    }

    @Test
    void operationProcess_validData_ok() {
        Storage.fruitsStorage.put(APPLE_KEY, 10);
        operationHandler.operationProcess(validData);
        assertEquals(20, Storage.fruitsStorage.get(APPLE_KEY));
    }

    @Test
    void operationProcess_numberLessThatZero_notOk() {
        RuntimeException expectedMessage = assertThrows(RuntimeException.class,
                () -> operationHandler.operationProcess(dataLessZero));
        assertEquals("Can't return nothing", expectedMessage.getMessage());
    }
}
