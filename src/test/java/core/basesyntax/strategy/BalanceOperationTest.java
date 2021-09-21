package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static Operation operation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operation = new BalanceOperation();
    }

    @Before
    public void setUp() throws Exception {
        Storage.data.clear();
    }

    @Test
    public void balanceOperation_setBalance_OK() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction(Transaction.Operation.BALANCE,
                new Fruit("apple"), 13);
        operation.apply(transaction);
        int expected = 13;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
