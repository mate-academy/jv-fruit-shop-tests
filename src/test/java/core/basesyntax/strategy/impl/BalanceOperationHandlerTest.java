package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balance;
    private static FruitTransaction validTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balance = new BalanceOperationHandler();
        validTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void calculate_balanceOperation_ok() {
        Storage.storage.clear();
        balance.operate(validTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = NullPointerException.class)
    public void calculate_null_notOk() {

        balance.operate(null);
    }
}
