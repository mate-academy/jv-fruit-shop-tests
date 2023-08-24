package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String VALID_FRUIT = "apple";
    private static final Integer VALID_QUANTITY = 100;
    private static OperationHandler balanceHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        balanceHandler = new BalanceOperationHandler(fruitDao);
    }

    @AfterAll
    static void afterAll() {
        Storage.storageFruit.clear();
    }

    @Test
    void processTransaction_balanceOperation_ok() {
        balanceHandler.processTransaction(VALID_FRUIT, VALID_QUANTITY);
        assertEquals(VALID_QUANTITY,fruitDao.get(VALID_FRUIT));
    }
}
