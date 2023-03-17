package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceCalculatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceCalculatorTest {
    private final TypeCalculatorStrategy balance = new BalanceCalculatorImpl();
    private final FruitTransaction validTransaction
            = new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 10);
    private final FruitTransaction invalidTransaction
            = new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 10);

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void calculate_balanceOperation_ok() {
        balance.calculate(validTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_anotherOperation_notOk() {
        balance.calculate(invalidTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = NullPointerException.class)
    public void calculate_null_notOk() {
        balance.calculate(null);
    }
}
