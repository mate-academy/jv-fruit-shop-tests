package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.PurchaseCalculatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseCalculatorTest {
    private final TypeCalculatorStrategy purchase = new PurchaseCalculatorImpl();
    private final FruitTransaction fruitTransaction
            = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 10);
    private final FruitTransaction invalidTransaction
            = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);

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
        purchase.calculate(fruitTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_anotherOperation_notOk() {
        purchase.calculate(invalidTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = NullPointerException.class)
    public void calculate_null_notOk() {
        purchase.calculate(null);
    }
}
