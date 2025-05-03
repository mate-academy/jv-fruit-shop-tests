package core.basesyntax.service.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static int QUANTITY = 100;
    private static String FRUIT_NAME = "apple";
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
        operationHandler = new BalanceOperationHandler(storageDao);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(FRUIT_NAME);
        fruitTransaction.setQuantity(QUANTITY);
    }

    @Test
    void handleValidFruit_Ok() {
        operationHandler.handle(fruitTransaction);
        assertEquals(QUANTITY, storageDao.getQuantity(FRUIT_NAME));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
