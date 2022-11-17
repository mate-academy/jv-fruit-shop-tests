package core.basesyntax.service.operations;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PurchaseTest {
    @Test
    public void checkPurchaseOperation_Ok() {
        storage.clear();
        Purchase purchase = new Purchase();
        storage.put("apple", 256);
        purchase.makeOperation("apple", 128);
        assertEquals(128, (int) storage.get("apple"));
        purchase.makeOperation("apple", 64);
        assertEquals(64, (int) storage.get("apple"));
        storage.clear();
    }
}
