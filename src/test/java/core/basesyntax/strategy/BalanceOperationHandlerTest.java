package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void operateBalance_checkDate_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("b", new Fruit("testFruit"), 20);
        operationHandler.apply(fruitTransaction);
        Integer expected = 20;
        Integer actual = Storage.storage.get(fruitTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @AfterClass
    public static void tearDown() {
        Storage.storage.clear();
    }
}
