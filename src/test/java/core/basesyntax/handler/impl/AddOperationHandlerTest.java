package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.handler.impl.impl.AddOperationHandler;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler addOperationHandler;
    private static Fruit fruit;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        addOperationHandler = new AddOperationHandler();
        fruit = new Fruit("apple");
        transaction = new Transaction("s", "apple", 15);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void addHandler_usualState_ok() {
        Storage.fruitStorage.put(fruit, 15);
        addOperationHandler.apply(transaction);
        Integer actual = Storage.fruitStorage.get(fruit);
        Integer expected = 30;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addHandler_nonExistentFruit_ok() {
        addOperationHandler.apply(transaction);
        Integer actual = Storage.fruitStorage.get(fruit);
        Integer expected = 15;
        Assert.assertEquals(actual, expected);
    }
}
