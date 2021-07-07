package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.handler.impl.impl.RemoveOperationHandler;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoveOperationHandlerTest {
    private static OperationHandler removeOperationHandler;
    private static Fruit fruit;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        removeOperationHandler = new RemoveOperationHandler();
        fruit = new Fruit("apple");
        transaction = new Transaction("r", "apple", 15);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void removeOperationHandlerUsualState_ok() {
        Storage.fruitStorage.put(fruit, 15);
        removeOperationHandler.apply(transaction);
        Integer expected = 0;
        Integer actual = Storage.fruitStorage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void removeOperationHandlerWithNonExistentFruit_notOk() {
        removeOperationHandler.apply(transaction);
    }
}
