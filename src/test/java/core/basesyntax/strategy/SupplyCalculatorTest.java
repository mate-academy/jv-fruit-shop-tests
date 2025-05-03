package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.SupplyCalculatorImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyCalculatorTest {
    private static TypeCalculatorStrategy supplyCalculator;
    private static FruitTransaction validTransaction;
    private static FruitTransaction invalidTransaction;

    @BeforeClass
    public static void beforeClass() {
        invalidTransaction
                = new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, "banana", 10);
        validTransaction
                = new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, "banana", 10);
        supplyCalculator = new SupplyCalculatorImpl();
    }

    @Before
    public void setUp() {
        Storage.storage.put("banana", 10);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void calculate_supplyOperation_ok() {
        supplyCalculator.calculate(validTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 20);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_anotherOperation_notOk() {
        supplyCalculator.calculate(invalidTransaction);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("banana", 10);
        Assert.assertEquals(expected, Storage.storage);
    }

    @Test(expected = NullPointerException.class)
    public void calculate_null_notOk() {
        supplyCalculator.calculate(null);
    }
}
