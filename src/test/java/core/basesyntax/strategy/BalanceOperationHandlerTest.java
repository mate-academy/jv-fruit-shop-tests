package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperation;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceOperation = new BalanceOperationHandler();
    }

    @Before
    public void setUp() throws Exception {
        Storage.data.clear();
    }

    @Test
    public void balanceOperation_setBalance_ok() {
        Fruit fruit = new Fruit("apple");
        Transaction transaction = new Transaction("b", fruit, 10);
        balanceOperation.apply(transaction);
        int expected = 10;
        int actual = Storage.data.get(fruit);
        Assert.assertEquals(expected, actual);
    }

}
