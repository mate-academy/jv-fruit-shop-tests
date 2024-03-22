package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private static final String APPLE_KEY = "apple";
    private static final int VALID_NUMBER_TO_BUY = 5;
    private static final int NUMBER_MORE_THAT_WE_HAVE_FRUITS = 20;
    private OperationHandler operationHandler;
    private FruitTransaction validData;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperation();
        validData = new FruitTransaction(Operation.PURCHASE, APPLE_KEY, 10);
        Storage.fruitsStorage.clear();
    }

    @Test
    void operationProcess_addToStorage_ok() {
        Storage.fruitsStorage.put(validData.getFruit(),validData.getQuantity());
        assertEquals(10, Storage.fruitsStorage.get(APPLE_KEY));
    }

    @Test
    void operationProcess_validData_ok() {
        Storage.fruitsStorage.put(validData.getFruit(),validData.getQuantity()
                - VALID_NUMBER_TO_BUY);
        assertEquals(5, Storage.fruitsStorage.get(validData.getFruit()));
    }

    @Test
    void operationProcess_BuyMoreThatWeHave_notOk() {
        Storage.fruitsStorage.put(validData.getFruit(),validData.getQuantity()
                - NUMBER_MORE_THAT_WE_HAVE_FRUITS);
        RuntimeException expectedMessage = assertThrows(RuntimeException.class,
                () -> operationHandler.operationProcess(validData));
        assertEquals("Can't do this operation, don't have enough fruits",
                expectedMessage.getMessage());
    }
}
