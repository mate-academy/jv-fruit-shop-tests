package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionInfo;
import core.basesyntax.strategy.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerImplTest {
    private static Operation balanceHandle;
    private static Fruit fruitForTest;

    @BeforeClass
    public static void beforeClass() {
        balanceHandle = new BalanceOperationHandlerImpl();
        fruitForTest = new Fruit("banana");
    }

    @Test
    public void addedBalance_isOk() {
        int excepted = 100;
        balanceHandle.handle(new TransactionInfo("b", fruitForTest, 100));
        int actual = Storage.storage.get(fruitForTest);
        Assert.assertEquals(excepted, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
