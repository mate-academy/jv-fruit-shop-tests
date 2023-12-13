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

class BalanceOperationHandlerTest {
    private static final FruitTransaction.Operation OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final String FRUIT_NAME = "banana";
    private static final int FRUIT_QUANTITY = 60;
    private static FruitStorageDao fruitStorageDao;
    private static OperationHandler balanceOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitStorageDao = new FruitStorageDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitStorageDao);
    }

    @AfterEach
    void afterEach() {
        fruitStorageDao.getFruits().clear();
    }

    @Test
    void handleOperation_correctBalanceCheck_Ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(OPERATION, FRUIT_NAME, FRUIT_QUANTITY);
        balanceOperationHandler.handleOperation(fruitTransaction);
        assertEquals(FRUIT_QUANTITY, Storage.storage.get(FRUIT_NAME));
    }
}
