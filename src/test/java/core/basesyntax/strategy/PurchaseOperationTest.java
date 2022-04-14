package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationTest {
    private Operation operation;
    private Transaction transaction;

    @Before
    public void setUp() {
        operation = new PurchaseOperation();
        Storage.fruitStorage.put("banana", 20);
    }

    @Test
    public void apply_Ok() {
        transaction = new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("banana"), 20);
        int actual = operation.apply(transaction);
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_NotEnoughQuantity_NotOk() {
        transaction = new Transaction(Transaction.Operation.PURCHASE,
                new Fruit("banana"), 40);
        operation.apply(transaction);
    }
}
