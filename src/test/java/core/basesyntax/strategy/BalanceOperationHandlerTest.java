package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 50);
    }

    @Test
    public void balanceOperationHandler_Ok() {
        Transaction transaction = new Transaction("b", "banana", 150);
        int expected = 150;
        int actual = new BalanceOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }
}
