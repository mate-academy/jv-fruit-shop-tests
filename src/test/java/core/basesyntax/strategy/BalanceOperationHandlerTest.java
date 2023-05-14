package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler handler;
    
    @Before
    public void setUp() {
        handler = new BalanceOperationHandler();
    }

    @Test
    public void balanceOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20);
        handler.operate(fruitTransaction);
        int expected = 20;
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(expected,actual);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
