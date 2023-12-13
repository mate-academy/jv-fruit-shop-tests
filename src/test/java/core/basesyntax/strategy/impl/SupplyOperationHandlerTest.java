package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static FruitTransaction.Operation OPERATION = FruitTransaction.Operation.SUPPLY;
    private static String FRUIT_NAME = "banana";
    private static int FRUIT_QUANTITY = 100;
    private static int SUPPLY_QUANTITY = 56;
    private static OperationHandler supplyHandler;
    private static FruitStorageDao fruitStorageDao;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        supplyHandler = new SupplyOperationHandler(fruitStorageDao);
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void handleOperation_CorrectQuantity_Ok() {
        fruitStorageDao.updateFruitQuantity(FRUIT_NAME, FRUIT_QUANTITY);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OPERATION, FRUIT_NAME, SUPPLY_QUANTITY);
        supplyHandler.handleOperation(fruitTransaction);
        assertEquals(FRUIT_QUANTITY + SUPPLY_QUANTITY,
                fruitStorageDao.getFruitQuantity(FRUIT_NAME));
    }
}
