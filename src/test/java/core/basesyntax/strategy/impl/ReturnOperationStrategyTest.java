package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationStrategyTest {
    private static ReturnOperationStrategy returnOperationStrategy;

    @BeforeClass
    public static void setUp() {
        returnOperationStrategy = new ReturnOperationStrategy();
        Storage.fruits.clear();
    }

    @Test
    public void calculate_validData_ok() {
        Storage.fruits.put("banana", 60);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 20);
        returnOperationStrategy.calculate(transaction);
        Integer expected = 80;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals("Wrong return data", expected, actual);
    }

    @Test
    public void calculate_wrongFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "orange", 20);
        returnOperationStrategy.calculate(transaction);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
