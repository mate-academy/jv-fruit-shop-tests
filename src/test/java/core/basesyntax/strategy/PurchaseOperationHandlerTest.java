package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void init() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void validPurchaseTransaction_Ok() {
        Fruit fruit = new Fruit();
        fruit.setName("banana");
        Storage.getStorage().put(fruit, 100);
        Transaction transaction = new Transaction("p", fruit, 50);
        purchaseOperationHandler.apply(transaction);
        int expected = 50;
        int actual = Storage.getStorage().get(fruit);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getStorage().clear();
    }
}
