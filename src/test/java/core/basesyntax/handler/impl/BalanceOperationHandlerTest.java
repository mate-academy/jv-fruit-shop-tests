package core.basesyntax.handler.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.handler.impl.impl.BalanceOperationHandler;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;
    private static Fruit fruit;
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
        fruit = new Fruit("apple");
        transaction = new Transaction("b", "apple", 15);
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void balanceHandlerUsualState_ok() {
        balanceOperationHandler.apply(transaction);
        Integer expected = 15;
        Integer actual = Storage.fruitStorage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balanceHandlerWithExistedFruit_ok() {
        Storage.fruitStorage.put(fruit, 7);
        balanceOperationHandler.apply(transaction);
        Integer expected = 15;
        Integer actual = Storage.fruitStorage.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
