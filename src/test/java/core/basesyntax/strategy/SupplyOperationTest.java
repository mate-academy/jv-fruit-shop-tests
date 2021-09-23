package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationTest {
    private static Operation operation;

    @BeforeClass
    public static void beforeClass() {
        operation = new SupplyOperation();
    }

    @Before
    public void setUp() {
        Storage.data.clear();
        Storage.data.put(new Fruit("apple"), 13);
    }

    @Test
    public void supplyOperation_Supply_OK() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction(Transaction.Operation.SUPPLY,
                fruit, 13);
        operation.apply(transaction);
        int expected = 26;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnOperation_Return_OK() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction(Transaction.Operation.RETURN,
                fruit, 13);
        operation.apply(transaction);
        int expected = 26;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
