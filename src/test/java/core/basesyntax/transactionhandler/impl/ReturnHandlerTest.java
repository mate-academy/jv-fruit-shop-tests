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

class ReturnHandlerTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final TransactionHandler returnHandler = new ReturnHandler(storageDao);
    private FruitTransaction returnTransaction;

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        Storage.fruitStorage.put("banana", 65);
        returnTransaction = new FruitTransaction("b", "banana", 10);
        int expected = Storage.fruitStorage.get("banana") + returnTransaction.getQuantity();
        returnHandler.handleTransaction(returnTransaction);
        int actual = Storage.fruitStorage.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        assertThrows(FruitShopException.class,
                () -> returnHandler.handleTransaction(null));
    }

    @Test
    void handleTransaction_negativeTransaction_notOk() {
        returnTransaction = new FruitTransaction("b", "banana", -10);
        assertThrows(FruitShopException.class,
                () -> returnHandler.handleTransaction(returnTransaction));
    }
}
