package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class OperationHandlerTest {
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

    @Test
    public void supplyOperationHandler_Ok() {
        Transaction transaction = new Transaction("s", "banana", 150);
        int expected = 200;
        int actual = new AddOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }

    @Test
    public void balanceOperationHandler_Ok() {
        Transaction transaction = new Transaction("b", "banana", 150);
        int expected = 150;
        int actual = new BalanceOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }

    @Test
    public void returnOperationHandler_Ok() {
        Transaction transaction = new Transaction("r", "banana", 150);
        int expected = 200;
        int actual = new AddOperationHandler().apply(transaction);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
