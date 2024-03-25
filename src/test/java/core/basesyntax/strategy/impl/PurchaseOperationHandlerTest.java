package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.exception.IllegalInputDataException;
import core.basesyntax.exception.NotEnoughProductAmountException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final StorageDao STORAGE_DAO =
            new StorageDaoImpl();
    private static final OperationHandler PURCHASE_OPERATION_HANDLER
            = new PurchaseOperationHandler(STORAGE_DAO);
    private static final String FRUIT_APPLE = "apple";
    private static final FruitTransaction FRUIT_TRANSACTION
            = new FruitTransaction(Operation.PURCHASE, FRUIT_APPLE, 101);
    private static final FruitTransaction VALID_TRANSACTION
            = new FruitTransaction(Operation.PURCHASE, FRUIT_APPLE, 99);

    @BeforeEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void constructor_storageDaoIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> new PurchaseOperationHandler(null));

        assertEquals("Storage dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_fruitTransactionIsNull_notOk() {
        IllegalInputDataException expected = assertThrows(IllegalInputDataException.class,
                () -> PURCHASE_OPERATION_HANDLER.handle(null));

        assertEquals("Fruit transaction dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_fruitTransactionQuantityBiggerThenAvailableInStorage_notOk() {
        Storage.fruits.put("apple", 100);

        NotEnoughProductAmountException expected = assertThrows(
                NotEnoughProductAmountException.class,
                () -> PURCHASE_OPERATION_HANDLER.handle(FRUIT_TRANSACTION));

        assertEquals(
                String.format("No enough product %s amount: available - %d, needed - %d",
                FRUIT_TRANSACTION.fruit(),
                        Storage.fruits.get(FRUIT_APPLE),
                        FRUIT_TRANSACTION.quantity()),
                expected.getMessage());
    }

    @Test
    void handle_putProductWithNewQuantityToTheStorage_notOk() {
        Storage.fruits.put("apple", 100);

        PURCHASE_OPERATION_HANDLER.handle(VALID_TRANSACTION);

        assertEquals(1, Storage.fruits.get(FRUIT_APPLE));
    }
}
