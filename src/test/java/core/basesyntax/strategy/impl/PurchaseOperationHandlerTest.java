package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static FruitTransaction.Operation OPERATION = FruitTransaction.Operation.PURCHASE;
    private static String FRUIT_NAME = "banana";
    private static int FRUIT_QUANTITY = 100;
    private static int MAX_PURCHASED_QUANTITY = 100;
    private static int PURCHASED_QUANTITY = 56;
    private static int INCORRECT_PURCHASED_QUANTITY = 190;
    private static OperationHandler purchaseHandler;
    private static FruitStorageDao fruitStorageDao;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        purchaseHandler = new PurchaseOperationHandler(fruitStorageDao);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void purchaseOperationHandler_tooBigPurchasedQuantity_notOk() {
        fruitStorageDao.updateFruitQuantity(FRUIT_NAME, FRUIT_QUANTITY);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OPERATION, FRUIT_NAME, INCORRECT_PURCHASED_QUANTITY);
        assertThrows(RuntimeException.class, ()
                -> purchaseHandler.handleOperation(fruitTransaction));
    }

    @Test
    void purchaseOperationHandler_correctPurchasedQuantity_Ok() {
        fruitStorageDao.updateFruitQuantity(FRUIT_NAME, FRUIT_QUANTITY);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OPERATION, FRUIT_NAME, PURCHASED_QUANTITY);
        purchaseHandler.handleOperation(fruitTransaction);
        assertEquals(FRUIT_QUANTITY - PURCHASED_QUANTITY,
                fruitStorageDao.getFruitQuantity(FRUIT_NAME));
    }

    @Test
    void purchaseOperationHandler_maxAllowedPurchasedQuantity_Ok() {
        fruitStorageDao.updateFruitQuantity(FRUIT_NAME, FRUIT_QUANTITY);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OPERATION, FRUIT_NAME, MAX_PURCHASED_QUANTITY);
        purchaseHandler.handleOperation(fruitTransaction);
        assertEquals(FRUIT_QUANTITY - MAX_PURCHASED_QUANTITY,
                fruitStorageDao.getFruitQuantity(FRUIT_NAME));
    }
}
