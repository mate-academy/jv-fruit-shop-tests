package core.basesyntax.service.strategy.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static int STARTED_QUANTITY = 100;
    private static int SUPPLIED_QUANTITY = 30;
    private static int EXPECTED_QUANTITY = 130;
    private static String FRUIT_NAME = "apple";
    private static StorageDao storageDao;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandler = new SupplyOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(SUPPLIED_QUANTITY);
    }

    @Test
    void handleValidFruit_Ok() {
        Storage.FRUITS.put(FRUIT_NAME, STARTED_QUANTITY);
        operationHandler.handle(fruitTransaction);
        Assertions.assertEquals(EXPECTED_QUANTITY, storageDao.getQuantity(FRUIT_NAME));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
