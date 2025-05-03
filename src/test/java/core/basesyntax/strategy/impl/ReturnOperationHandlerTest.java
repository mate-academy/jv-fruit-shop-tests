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
    private static final String FRUIT_APPLE
            = "apple";
    private static final int FRUIT_QUANTITY_BEFORE_RETURN
            = 100;
    private static final int FRUIT_QUANTITY_AFTER_RETURN
            = 200;
    private final StorageDao storageDao
            = new StorageDaoImpl();
    private final OperationHandler operationHandler
            = new ReturnOperationHandler(storageDao);
    private final FruitTransaction fruitTransaction
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
                () -> operationHandler.handle(null));

        assertEquals("Fruit transaction dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_putProductWithNewQuantityToTheStorage_notOk() {
        Storage.fruits.put(FRUIT_APPLE, 100);

        operationHandler.handle(fruitTransaction);

        assertEquals(FRUIT_QUANTITY_AFTER_RETURN, Storage.fruits.get(FRUIT_APPLE));
    }
}
