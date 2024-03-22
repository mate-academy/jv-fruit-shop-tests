package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final StorageDao storageDao
            = new StorageDaoImpl();
    private static final OperationHandler operationHandler =
            new BalanceOperationHandler(storageDao);
    private static final String FRUIT_APPLE = "apple";
    private static final FruitTransaction fruitTransaction
            = new FruitTransaction(Operation.BALANCE, FRUIT_APPLE, 100);

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void constructor_storageDaoIsNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> new BalanceOperationHandler(null));
    }

    @Test
    void handle_fruitTransactionIsNull_notOk() {
        OperationHandler invalidOperationHandler = new BalanceOperationHandler(storageDao);
        assertThrows(NullPointerException.class,
                () -> invalidOperationHandler.handle(null));
    }

    @Test
    void handle_putProductToTheStorage_ok() {
        operationHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get(FRUIT_APPLE);
        assertEquals(100, actual);
    }
}
