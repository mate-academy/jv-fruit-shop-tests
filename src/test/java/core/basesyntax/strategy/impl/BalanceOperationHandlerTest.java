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

class BalanceOperationHandlerTest {
    private static final StorageDao storageDao
            = new StorageDaoImpl();
    private static final OperationHandler operationHandler =
            new BalanceOperationHandler(storageDao);
    private static final String FRUIT_APPLE = "apple";
    private static final FruitTransaction fruitTransaction
            = new FruitTransaction(Operation.BALANCE, FRUIT_APPLE, 100);

    @BeforeEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void constructor_storageDaoIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> new BalanceOperationHandler(null));

        assertEquals("Storage dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_fruitTransactionIsNull_notOk() {
        OperationHandler invalidOperationHandler = new BalanceOperationHandler(storageDao);

        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> invalidOperationHandler.handle(null));

        assertEquals("Fruit transaction dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_putProductToTheStorage_ok() {
        operationHandler.handle(fruitTransaction);

        Integer actual = Storage.fruits.get(FRUIT_APPLE);

        assertEquals(100, actual);
    }
}
