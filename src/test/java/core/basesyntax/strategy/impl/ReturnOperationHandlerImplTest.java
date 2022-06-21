package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionInfo;
import core.basesyntax.strategy.Operation;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static Operation returnHandle;
    private static Fruit testFruit;

    @BeforeClass
    public static void beforeClass() {
        returnHandle = new ReturnOperationHandlerImpl();
        testFruit = new Fruit("banana");
    }

    @Before
    public void setUp() {
        Storage.storage.put(testFruit, 200);
    }

    @Test
    public void changeBalanceReturn_isOk() {
        int excepted = 300;
        TransactionInfo transactionInfo = new TransactionInfo("p", testFruit, 100);
        returnHandle.handle(transactionInfo);
        int actual = Storage.storage.get(testFruit);
        Assert.assertEquals(excepted, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
