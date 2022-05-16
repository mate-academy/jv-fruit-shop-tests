package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static OperationHandler returnHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        returnHandler = new ReturnHandler();
    }

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(20);
        returnHandler.handle(fruitTransaction);
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
        returnHandler.handle(fruitTransaction);
    }
}
