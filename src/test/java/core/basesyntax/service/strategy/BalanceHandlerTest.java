package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static OperationHandler balanceHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceHandler();
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(20);
        balanceHandler.handle(fruitTransaction);
    }

    @Test
    public void handle_validData_Ok() {
        Assert.assertTrue(Storage.fruits.containsKey(new Fruit("apple")));
    }

    @Test
    public void handle_NotExistedFruit_notOk() {
        Assert.assertFalse(Storage.fruits.containsKey(new Fruit("banana")));
    }

    @Test(expected = NullPointerException.class)
    public void handle_null_notOk() {
        FruitTransaction fruitTransaction = null;
        balanceHandler.handle(fruitTransaction);
    }
}
