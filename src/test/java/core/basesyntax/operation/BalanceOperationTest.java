package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import core.basesyntax.storage.StorageImpl;
import core.basesyntax.transactions.FruitTransaction;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private FruitTransaction fruitTransaction;
    private BalanceOperation balanceOperation;
    private Storage storage;

    @Test
    void operate_nullFruitTransaction_notOk() {
        fruitTransaction = null;
        balanceOperation = new BalanceOperation();
        storage = new StorageImpl();
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.operate(fruitTransaction, storage));
    }

    @Test
    void operate_nullStorage_notOk() {
        fruitTransaction = new FruitTransaction();
        balanceOperation = new BalanceOperation();
        assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.operate(fruitTransaction, null));
    }

    @Test
    void operate_existingFruit_replaceQuantity_ok() {
        storage = new StorageImpl();
        balanceOperation = new BalanceOperation();

        FruitTransaction actual = new FruitTransaction();
        actual.setFruit("banana");
        actual.setQuantity(10);

        FruitTransaction expected = new FruitTransaction();
        expected.setFruit("banana");
        expected.setQuantity(20);

        balanceOperation.operate(actual, storage);
        balanceOperation.operate(expected, storage);

        assertEquals(20, storage.getQuantity("banana"));
    }
}
