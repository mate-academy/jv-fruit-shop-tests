package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import org.junit.BeforeClass;
import org.junit.Test;


public class PurchaseOperationHandlerTest {
    private static Transaction transaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        transaction = new Transaction("p", new Fruit("apple"), 9);
    }

    @Test
    public void applyPurchase_OK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        Integer currentQuantity = Storage.storage.get(apple);
        Storage.storage.put(transaction.getFruit(), currentQuantity - transaction.getQuantity());
        assertEquals(Integer.valueOf(5), Storage.storage.get(apple));
    }

    @Test
    public void negativeValue_NotOK() {
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 14);
        Integer currentQuantity = Storage.storage.get(apple);
        transaction.setQuantity(-9);
        Storage.storage.put(transaction.getFruit(), currentQuantity - transaction.getQuantity());
        assertFalse(Integer.valueOf(5).equals(Storage.storage.get(apple)));
    }
}