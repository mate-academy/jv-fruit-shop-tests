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

class ReturnOperationHandlerTest {
    private static final StorageDao STORAGE_DAO
            = new StorageDaoImpl();
    private static final OperationHandler OPERATION_HANDLER
            = new ReturnOperationHandler(STORAGE_DAO);
    private static final String FRUIT_APPLE
            = "apple";
    private static final int FRUIT_QUANTITY_BEFORE_RETURN
            = 100;
    private static final int FRUIT_QUANTITY_AFTER_RETURN
            = 200;
    private static final FruitTransaction FRUIT_TRANSACTION
            = new FruitTransaction(Operation.RETURN, FRUIT_APPLE, FRUIT_QUANTITY_BEFORE_RETURN);

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void constructor_storageDaoIsNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> new ReturnOperationHandler(null));
    }

    @Test
    void handle_fruitTransactionIsNull_notOk() {
        assertThrows(NullPointerException.class,
                () -> OPERATION_HANDLER.handle(null));
    }

    @Test
    void handle_putProductWithNewQuantityToTheStorage_notOk() {
        Storage.fruits.put(FRUIT_APPLE, 100);
        OPERATION_HANDLER.handle(FRUIT_TRANSACTION);
        assertEquals(FRUIT_QUANTITY_AFTER_RETURN, Storage.fruits.get(FRUIT_APPLE));
    }
}
