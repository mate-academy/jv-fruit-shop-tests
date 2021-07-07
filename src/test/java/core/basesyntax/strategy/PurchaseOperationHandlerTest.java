package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    @Before
    public void setUp() {
        Storage.fruits.put(new Fruit("banana"), 50);
    }

    @Test
    public void purchaseOperationHandler_Ok() {
        Transaction transaction = new Transaction("p", "banana", 20);
        int expected = 30;
        int actual = new PurchaseOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }
}
