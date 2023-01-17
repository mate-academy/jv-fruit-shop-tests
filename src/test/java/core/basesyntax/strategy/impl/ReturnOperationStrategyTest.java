package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationStrategyTest {
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    public void calculate_validData_ok() {
        Storage.fruits.put("banana", 60);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "banana", 20);
        returnOperationHandler.calculate(transaction);
        Integer expected = 80;
        Integer actual = Storage.fruits.get("banana");
        Assert.assertEquals("Wrong return data", expected, actual);
    }

    @Test
    public void calculate_wrongFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.RETURN,
                "orange", 20);
        returnOperationHandler.calculate(transaction);
    }

    @After
    public void clearStorage() {
        Storage.fruits.clear();
    }
}
