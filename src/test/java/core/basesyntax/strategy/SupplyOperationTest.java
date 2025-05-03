package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationTest {
    private Operation operation;
    private Transaction transaction;

    @Before
    public void setUp() {
        operation = new SupplyOperation();
        transaction = new Transaction(Transaction.Operation.SUPPLY,
                new Fruit("banana"), 20);
        Storage.fruitStorage.put("banana", 20);
    }

    @Test
    public void apply_Ok() {
        int actual = operation.apply(transaction);
        int expected = 40;
        assertEquals(expected, actual);
    }
}
