package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitTransactionException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final String VALID_FRUIT_NAME = "banana";
    private static final Integer VALID_FRUIT_AMOUNT = 10;
    private static final String OPERATION_CODE = "p";
    private static final Integer ZERO_FRUIT_AMOUNT = 0;
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseOperationHandler();
        Storage.STORAGE.clear();
    }

    @Test
    void executeOperation_ValidData_ok() {
        Map<String, Integer> expected = Map.of(VALID_FRUIT_NAME, ZERO_FRUIT_AMOUNT);
        Storage.STORAGE.put(VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT);

        operationHandler.executeOperation(new FruitTransaction(
                OPERATION_CODE, VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT));
        Map<String, Integer> actual = Storage.STORAGE;

        assertEquals(expected, actual);
    }

    @Test
    void executeOperation_notEnoughAmount_notOk() {
        FruitTransaction invalidTransaction = new FruitTransaction(
                OPERATION_CODE, VALID_FRUIT_NAME, VALID_FRUIT_AMOUNT);
        FruitTransactionException fruitTransactionException =
                assertThrows(FruitTransactionException.class,
                        () -> operationHandler.executeOperation(invalidTransaction));
        assertEquals(fruitTransactionException.getMessage(),
                "Can't execute the subtraction operation. Fruit amount of "
                        + VALID_FRUIT_NAME + " is " + ZERO_FRUIT_AMOUNT
                        + ", but subtraction amount is " + VALID_FRUIT_AMOUNT);
    }
}
