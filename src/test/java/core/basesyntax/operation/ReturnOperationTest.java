package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.storage.StorageImpl;
import core.basesyntax.transactions.FruitTransaction;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private FruitTransaction fruitTransaction;
    private ReturnOperation returnOperation;
    private Storage storage;

    @Test
    void operate_nullFruitTransaction_notOk() {
        fruitTransaction = null;
        returnOperation = new ReturnOperation();
        storage = new StorageImpl();
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.operate(fruitTransaction, storage));
    }

    @Test
    void operate_nullStorage_notOk() {
        fruitTransaction = new FruitTransaction();
        returnOperation = new ReturnOperation();
        assertThrows(IllegalArgumentException.class,
                () -> returnOperation.operate(fruitTransaction, null));
    }

    @Test
    void operate_increaseFruitQuantityAfterReturn_ok() {
        storage = new StorageImpl();
        storage.put("banana", 10);
        returnOperation = new ReturnOperation();
        FruitTransaction transaction = new FruitTransaction();
        transaction.setFruit("banana");
        transaction.setQuantity(20);
        returnOperation.operate(transaction, storage);
        assertEquals(30, storage.getQuantity("banana"));
    }
}
