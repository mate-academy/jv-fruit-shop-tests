package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperation;

    @BeforeClass
    public static void beforeClass() {
        supplyOperation = new SupplyOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.data.clear();
        Storage.data.put(new Fruit("apple"),10);
    }

    @Test
    public void supplyOperation_supply_ok() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction("s", fruit, 5);
        supplyOperation.apply(transaction);
        int expected = 15;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyOperation_return_ok() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction("r", fruit, 5);
        supplyOperation.apply(transaction);
        int expected = 15;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
