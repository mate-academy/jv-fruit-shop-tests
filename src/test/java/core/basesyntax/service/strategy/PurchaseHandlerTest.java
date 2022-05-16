package core.basesyntax.service.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static OperationHandler purchaseHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        purchaseHandler = new PurchaseHandler();
        fruitTransaction = new FruitTransaction();
        Storage.fruits.put(new Fruit("apple"), 100);
    }

    @Test
    public void handle_validData_Ok() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(20);
        purchaseHandler.handle(fruitTransaction);
        Assert.assertTrue(Storage.fruits.containsKey(new Fruit("apple")));
        Integer appleActual = Storage.fruits.get(new Fruit("apple"));
        Integer expected = 80;
        Assert.assertEquals(expected, appleActual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_amountMoreThanExisted_notOk() {
        fruitTransaction.setFruit("apple");
        fruitTransaction.setQuantity(120);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test
    public void handle_NotExistedFruit_notOk() {
        fruitTransaction.setFruit("banana");
        Assert.assertFalse(Storage.fruits.containsKey(fruitTransaction.getFruit()));
    }

    @Test(expected = NullPointerException.class)
    public void handle_null_notOk() {
        FruitTransaction fruitTransaction = null;
        purchaseHandler.handle(fruitTransaction);
    }
}
