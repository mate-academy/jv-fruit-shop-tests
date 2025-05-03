package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private FruitTransaction fruitTransaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void apply_FruitTransactionIsValid_Ok() {
        fruitTransaction = new FruitTransaction("p", new Fruit("banana"), 10);
        fruit = new Fruit("banana");
        Storage.storage.put(fruit, 5);
        purchaseOperationHandler.apply(fruitTransaction);
        Integer expected = -5;
        Integer actual = Storage.storage.get(fruit);
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void apply_FruitTransactionIsNull_NotOk() {
        purchaseOperationHandler.apply(null);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

}
