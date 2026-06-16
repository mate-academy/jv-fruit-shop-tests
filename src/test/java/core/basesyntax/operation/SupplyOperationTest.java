package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.storage.StorageImpl;
import core.basesyntax.transactions.FruitTransaction;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private FruitTransaction fruitTransaction;
    private Storage storage;

    @Test
    void operate_nullFruitTransaction_notOk() {
        fruitTransaction = null;
        SupplyOperation supplyOperation = new SupplyOperation();
        storage = new StorageImpl();
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.operate(fruitTransaction, storage));
    }

    @Test
    void operate_nullStorage_notOk() {
        fruitTransaction = new FruitTransaction();
        SupplyOperation supplyOperation = new SupplyOperation();
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperation.operate(fruitTransaction, null));
    }

    @Test
    void operate_increaseFruitQuantityAfterReturn_ok() {
        storage = new StorageImpl();
        storage.put("banana", 10);
        SupplyOperation supplyOperation = new SupplyOperation();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(20);
        supplyOperation.operate(transaction, storage);
        assertEquals(30, storage.getQuantity("banana"));
    }
}
