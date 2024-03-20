package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static final String APPLE_KEY = "apple";
    private OperationHandler operationHandler;
    private FruitTransaction validData;
    private FruitTransaction inValidData;

    @BeforeEach
    void setUp() {
        operationHandler = new SupplyOperation();
        validData = new FruitTransaction(Operation.SUPPLY, APPLE_KEY, 10);
        inValidData = new FruitTransaction(Operation.SUPPLY, APPLE_KEY, -6);
    }

    @Test
    void operationProcess_validData_ok() {
        Storage.fruitsStorage.put(APPLE_KEY, 10);
        operationHandler.operationProcess(validData);
        assertEquals(20, Storage.fruitsStorage.get(APPLE_KEY));
    }

    @Test
    void operationProcess_quantityLessThanZero_notOk() {
        RuntimeException expectedMessage = assertThrows(RuntimeException.class,
                () -> operationHandler.operationProcess(inValidData));
        assertEquals("Can't add negative quantity of fruits", expectedMessage.getMessage());
    }
}
