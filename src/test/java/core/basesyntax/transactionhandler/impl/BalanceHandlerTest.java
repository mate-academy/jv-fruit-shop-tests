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

class BalanceHandlerTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final TransactionHandler balanceHandler = new BalanceHandler(storageDao);
    private FruitTransaction balanceTransaction;

    @AfterEach
    void tearDown() {
        Storage.fruitStorage.clear();
    }

    @Test
    void handleTransaction_validTransaction_ok() {
        balanceTransaction = new FruitTransaction("b", "banana", 68);
        balanceHandler.handleTransaction(balanceTransaction);
        int expected = balanceTransaction.getQuantity();
        int actual = Storage.fruitStorage.get(balanceTransaction.getFruit());
        assertEquals(expected, actual);
    }

    @Test
    void handleTransaction_nullTransaction_notOk() {
        assertThrows(FruitShopException.class,
                () -> balanceHandler.handleTransaction(null));
    }

    @Test
    void handleTransaction_negativeAmount_notOk() {
        balanceTransaction = new FruitTransaction("b", "banana", -68);
        assertThrows(FruitShopException.class,
                () -> balanceHandler.handleTransaction(balanceTransaction));
    }
}
