package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final FruitTransaction.Operation OPERATION
            = FruitTransaction.Operation.RETURN;
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 60;
    private static final int RETURNED_FRUIT_QUANTITY = 20;
    private static FruitStorageDao fruitStorageDao;
    private static OperationHandler returnOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        returnOperationHandler = new ReturnOperationHandler(fruitStorageDao);
    }

    @AfterEach
    void afterEach() {
        fruitStorageDao.getFruits().clear();
    }

    @Test
    void handleOperation_correctQuantity_Ok() {
        fruitStorageDao.updateFruitQuantity(FRUIT_NAME, FRUIT_QUANTITY);
        FruitTransaction fruitTransaction
                = new FruitTransaction(OPERATION, FRUIT_NAME, RETURNED_FRUIT_QUANTITY);
        returnOperationHandler.handleOperation(fruitTransaction);
        assertEquals(RETURNED_FRUIT_QUANTITY + FRUIT_QUANTITY,
                fruitStorageDao.getFruitQuantity(FRUIT_NAME));
    }
}
