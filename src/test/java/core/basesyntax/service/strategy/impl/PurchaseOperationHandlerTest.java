package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static int STARTED_QUANTITY = 100;
    private static int INVALID_QUANTITY = 101;
    private static int PURCHASED_QUANTITY = 30;
    private static int EXPECTED_QUANTITY = 70;
    private static String FRUIT_NAME = "apple";
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandler = new PurchaseOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(PURCHASED_QUANTITY);
    }

    @Test
    void handleValidFruit_Ok() {
        Storage.FRUITS.put(FRUIT_NAME, STARTED_QUANTITY);
        operationHandler.handle(fruitTransaction);
        assertEquals(EXPECTED_QUANTITY, Storage.FRUITS.get(FRUIT_NAME));
    }

    @Test
    void handleInvalidQuantity_NotOk() {
        Storage.FRUITS.put(FRUIT_NAME, STARTED_QUANTITY);
        fruitTransaction.setQuantity(INVALID_QUANTITY);
        assertThrows(IllegalArgumentException.class,
                () -> operationHandler.handle(fruitTransaction));

    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
