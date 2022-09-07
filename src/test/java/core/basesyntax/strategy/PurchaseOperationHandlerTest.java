package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler purchaseHandler;
    private Fruit fruitKiwi;
    private Fruit fruitPie;

    @Before
    public void setUp() {
        Storage.storage.clear();
        purchaseHandler = new PurchaseOperationHandler();
        fruitKiwi = new Fruit("kiwi");
        fruitPie = new Fruit("pie");
    }

    @After
    public void cleanStorage() {
        Storage.storage.clear();
    }

    @Test
    public void handle_PurchaseCheck_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("p", fruitKiwi, 22);
        Storage.storage.put(fruitKiwi, 30);
        purchaseHandler.handle(fruitTransaction);
        Integer expected = 8;
        Integer actual = Storage.storage.get(fruitKiwi);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_PurchaseMoreThanBalance_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction("p", fruitKiwi, 32);
        Storage.storage.put(fruitKiwi, 30);
        purchaseHandler.handle(fruitTransaction);
    }

    @Test(expected = RuntimeException.class)
    public void handle_PurchaseAbsentFruitInStorage_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction("p", fruitPie, 10);
        purchaseHandler.handle(fruitTransaction);
    }
}
