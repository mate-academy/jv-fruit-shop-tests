package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
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

    @BeforeEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void constructor_storageDaoIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> new ReturnOperationHandler(null));

        assertEquals("Storage dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_fruitTransactionIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> OPERATION_HANDLER.handle(null));

        assertEquals("Fruit transaction dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_putProductWithNewQuantityToTheStorage_notOk() {
        Storage.fruits.put(FRUIT_APPLE, 100);

        OPERATION_HANDLER.handle(FRUIT_TRANSACTION);

        assertEquals(FRUIT_QUANTITY_AFTER_RETURN, Storage.fruits.get(FRUIT_APPLE));
    }
}
