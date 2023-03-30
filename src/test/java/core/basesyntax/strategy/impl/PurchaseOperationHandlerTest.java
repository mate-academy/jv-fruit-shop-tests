package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchase;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        purchase = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Before
    public void setUp() {
        Storage.storage.put("banana", 20);
    }

    @Test
    public void calculate_balanceOperation_ok() {
        purchase.operate(fruitTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = NullPointerException.class)
    public void calculate_null_notOk() {
        purchase.operate(null);
    }
}
