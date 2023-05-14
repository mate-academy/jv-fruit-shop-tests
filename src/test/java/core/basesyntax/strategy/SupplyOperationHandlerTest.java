package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class SupplyOperationHandlerTest {
    private OperationHandler operationHandler;
    private FruitTransaction validFruitTransaction;

    @Before
    public void init() {
        Storage.fruits.put("apple", 10);
        operationHandler = new SupplyOperationHandler();
        validFruitTransaction = new FruitTransaction(Operation.SUPPLY, "apple", 50);
    }

    @Test
    public void handle_ValidSupplyTransaction_Ok() {
        operationHandler.handle(validFruitTransaction);
        Integer expected = 60;
        Integer actual = Storage.fruits.get(validFruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @AfterAll
    public static void clear() {
        Storage.fruits.clear();
    }
}
