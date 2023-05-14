package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterAll;

public class BalanceOperationHandlerTest {
    private FruitTransaction validFruitTransaction;
    private OperationHandler operationHandler;

    @Before
    public void setUp() {
        validFruitTransaction = new FruitTransaction(Operation.BALANCE, "apply", 77);
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void handle_ValidBalanceTransaction_Ok() {
        operationHandler.handle(validFruitTransaction);
        Integer expected = 77;
        Integer actual = Storage.fruits.get(validFruitTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @AfterAll
    public static void clear() {
        Storage.fruits.clear();
    }
}
