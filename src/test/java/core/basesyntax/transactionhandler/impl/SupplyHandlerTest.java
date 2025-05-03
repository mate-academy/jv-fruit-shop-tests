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

class SupplyHandlerTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final TransactionHandler supplyHandler = new SupplyHandler(storageDao);
    private FruitTransaction supplyTransaction;

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        Storage.fruitStorage.put("banana", 11);
        supplyTransaction = new FruitTransaction("b", "banana", 129);
        int expected = Storage.fruitStorage.get("banana") + supplyTransaction.getQuantity();
        supplyHandler.handleTransaction(supplyTransaction);
        int actual = Storage.fruitStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        assertThrows(FruitShopException.class,
                () -> supplyHandler.handleTransaction(null));
    }

    @Test
    void handleTransaction_negativeTransaction_notOk() {
        supplyTransaction = new FruitTransaction("b", "banana", -97);
        assertThrows(FruitShopException.class,
                () -> supplyHandler.handleTransaction(supplyTransaction));
    }
}
