package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction validFruitTransaction;
    private FruitTransaction invalidFruitTransaction;

    @Before
    public void init() {
        Storage.fruits.put("apple", 50);
        operationHandler = new PurchaseOperationHandler();
        validFruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
        invalidFruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 60);

    }

    @Test
    public void handle_ValidPurchaseTransaction_Ok() {
        operationHandler.handle(validFruitTransaction);
        Integer expected = 40;
        Integer actual = Storage.fruits.get(validFruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    public void handle_invalidPurchaseTransaction_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> operationHandler.handle(invalidFruitTransaction));
        String expectedMessage = "Not enough " + invalidFruitTransaction.getFruit();
        String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
    }

    @AfterAll
    public static void clear() {
        Storage.fruits.clear();
    }
}
