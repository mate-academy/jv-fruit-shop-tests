package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class PurchaseOperationHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction validFruitTransaction;

    @Before
    public void init() {
        Storage.fruits.put("apple", 50);
        operationHandler = new PurchaseOperationHandler();
        validFruitTransaction = new FruitTransaction(Operation.PURCHASE, "apple", 10);
    }

    @Test
    public void handle_ValidPurchaseTransaction_Ok() {
        operationHandler.handle(validFruitTransaction);
        Integer expected = 40;
        Integer actual = Storage.fruits.get(validFruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @AfterAll
    public static void clear() {
        Storage.fruits.clear();
    }
}
