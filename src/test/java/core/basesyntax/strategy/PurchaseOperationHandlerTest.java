package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitDto;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;
    private static Fruit apple;
    private static Fruit banana;

    @BeforeClass
    public static void beforeClass() {
        handler = new PurchaseOperationHandler();
        apple = new Fruit("apple");
        banana = new Fruit("banana");
        Storage.storage.put(apple, 25);
        Storage.storage.put(banana, 54);
    }

    @Test
    public void purchaseHandler_addTransactionWithEnoughQuantity_Ok() {
        FruitDto fruitDto = new FruitDto("p", "apple", 1);
        handler.apply(fruitDto);
        int expected = 24;
        int actual = Storage.storage.get(apple);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void purchaseHandler_addTransactionWithNotEnoughQuantity_notOk() {
        FruitDto fruitDto = new FruitDto("p", "banana", 80);
        handler.apply(fruitDto);
    }

    @After
    public void erase() {
        Storage.storage.clear();
    }
}
