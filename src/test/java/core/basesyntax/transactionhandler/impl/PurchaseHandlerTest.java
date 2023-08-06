package core.basesyntax.transactionhandler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.FruitShopException;
import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.transactionhandler.TransactionHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final TransactionHandler purchaseHandler = new PurchaseHandler(storageDao);
    private FruitTransaction purchaseTransaction;

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handleTransaction_validTransactionAndEnoughFruitInStorage_ok() {
        Storage.fruitStorage.put("banana", 100);
        purchaseTransaction = new FruitTransaction("b", "banana", 33);
        int expected = Storage.fruitStorage.get("banana") - purchaseTransaction.getQuantity();
        purchaseHandler.handleTransaction(purchaseTransaction);
        int actual = Storage.fruitStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_validTransactionAndNotEnoughFruitInStorage_notOk() {
        Storage.fruitStorage.put("banana", 1);
        purchaseTransaction = new FruitTransaction("b", "banana", 33);
        assertThrows(FruitShopException.class,
                () -> purchaseHandler.handleTransaction(purchaseTransaction));
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        assertThrows(FruitShopException.class,
                () -> purchaseHandler.handleTransaction(null));
    }

    @Test
    void handleTransaction_negativeTransaction_notOk() {
        purchaseTransaction = new FruitTransaction("b", "banana", -33);
        assertThrows(FruitShopException.class,
                () -> purchaseHandler.handleTransaction(purchaseTransaction));
    }
}
