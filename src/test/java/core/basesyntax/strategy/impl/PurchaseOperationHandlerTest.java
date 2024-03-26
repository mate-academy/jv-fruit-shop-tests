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
    private static final String FRUIT_APPLE = "apple";
    private final StorageDao storageDao =
            new StorageDaoImpl();
    private final OperationHandler purchaseOperationHandler
            = new PurchaseOperationHandler(storageDao);
    private final FruitTransaction invalidFruitTransaction
            = new FruitTransaction(Operation.PURCHASE, FRUIT_APPLE, 101);
    private final FruitTransaction validFruitTransaction
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
                () -> purchaseOperationHandler.handle(null));

        assertEquals("Fruit transaction dao can`t be null", expected.getMessage());
    }

    @Test
    void handle_fruitTransactionQuantityBiggerThenAvailableInStorage_notOk() {
        Storage.fruits.put("apple", 100);

        NotEnoughProductAmountException expected = assertThrows(
                NotEnoughProductAmountException.class,
                () -> purchaseOperationHandler.handle(invalidFruitTransaction));

        assertEquals(
                String.format("No enough product %s amount: available - %d, needed - %d",
                invalidFruitTransaction.fruit(),
                        Storage.fruits.get(FRUIT_APPLE),
                        invalidFruitTransaction.quantity()),
                expected.getMessage());
    }

    @Test
    void handle_putProductWithNewQuantityToTheStorage_notOk() {
        Storage.fruits.put("apple", 100);

        purchaseOperationHandler.handle(validFruitTransaction);

        assertEquals(1, Storage.fruits.get(FRUIT_APPLE));
    }
}
