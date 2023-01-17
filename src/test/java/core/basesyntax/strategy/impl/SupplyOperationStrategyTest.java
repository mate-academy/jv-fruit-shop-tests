package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationStrategyTest {
    private static SupplyOperationStrategy supplyOperationStrategy;

    @BeforeClass
    public static void setUp() {
        supplyOperationStrategy = new SupplyOperationStrategy();
        Storage.fruits.clear();
    }

    @Test
    public void calculate_validData_ok() {
        Storage.fruits.put("banana", 60);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 20);
        supplyOperationStrategy.calculate(transaction);
        Integer expected = 80;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals("Wrong supply data", expected, actual);
    }
}
