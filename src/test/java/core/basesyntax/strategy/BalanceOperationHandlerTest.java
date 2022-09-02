package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static Storage fruitStorage;
    private static OperationHandler handler;

    @BeforeClass
    public static void setup() {
        fruitStorage = new Storage();
        handler = new BalanceOperationHandler();
    }

    @Test
    public void balance_operation_fruit_absent_OK() {
        Transaction transaction = new Transaction("b", new Fruit("banana"), 20);
        handler.apply(transaction);
        Integer expected = 20;
        Integer actual = fruitStorage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void balance_operation_fruit_present_OK() {
        fruitStorage.put(new Fruit("banana"), 30);
        Transaction transaction = new Transaction("b", new Fruit("banana"), 50);
        handler.apply(transaction);
        Integer expected = 50;
        Integer actual = fruitStorage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);

    }

    @Test
    public void balance_operation_fruit_present_balanceZero_OK() {
        fruitStorage.put(new Fruit("apple"), 40);
        Transaction transaction = new Transaction("b", new Fruit("apple"), 0);
        handler.apply(transaction);
        Integer expected = 0;
        Integer actual = fruitStorage.get(new Fruit("apple"));
        Assert.assertEquals(expected, actual);

    }

    @After
    public void tearDown() {
        fruitStorage.clear();
    }
}
