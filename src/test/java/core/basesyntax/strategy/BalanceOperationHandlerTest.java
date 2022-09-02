package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
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

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
