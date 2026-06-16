package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.storage.StorageImpl;
import core.basesyntax.transactions.FruitTransaction;
import org.junit.jupiter.api.Test;

class PurchaseOperationTest {
    private FruitTransaction fruitTransaction;
    private PurchaseOperation purchaseOperation;
    private Storage storage;

    @Test
    void operate_nullFruitTransaction_notOk() {
        fruitTransaction = null;
        purchaseOperation = new PurchaseOperation();
        storage = new StorageImpl();
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.operate(fruitTransaction, storage));
    }

    @Test
    void operate_nullStorage_notOk() {
        fruitTransaction = new FruitTransaction();
        purchaseOperation = new PurchaseOperation();
        assertThrows(IllegalArgumentException.class,
                () -> purchaseOperation.operate(fruitTransaction, null));
    }

    @Test
    void operate_fruitQuantityAfterPurchase_ok() {
        storage = new StorageImpl();
        storage.put("banana", 40);
        purchaseOperation = new PurchaseOperation();
        FruitTransaction expected = new FruitTransaction();
        expected.setFruit("banana");
        expected.setQuantity(20);
        purchaseOperation.operate(expected, storage);
        assertEquals(20, storage.getQuantity("banana"));
    }
}
