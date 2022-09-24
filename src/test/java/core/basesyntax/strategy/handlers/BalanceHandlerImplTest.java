package core.basesyntax.strategy.handlers;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Before;
import org.junit.Test;

public class BalanceHandlerImplTest extends BalanceHandlerImpl {
    private OperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceHandlerImpl();
    }

    @Test
    public void balanceHandler_ValidTransaction_OK() {
        FruitTransaction fruitTransaction = new FruitTransaction("banana", 20);
        balanceOperationHandler.handle(fruitTransaction);
        int expected = 20;
        int actual = Storage.storage.get("banana");
        assertEquals(expected, actual);
    }
}
