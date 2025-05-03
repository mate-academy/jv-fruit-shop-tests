package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String VALID_FRUIT = "banana";
    private static final String INVALID_FRUIT = "milk";
    private static final int PREVIOUS_QUANTITY = 30;
    private static final int VALID_QUANTITY = 20;
    private static final int INVALID_QUANTITY = 100;
    private static final int NULL_QUANTITY = 0;
    private static Storage storage;
    private static FruitDao fruitDao;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void init() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        operationHandler = new PurchaseOperationHandler(fruitDao);
    }

    @BeforeEach
    void initEach() {
        fruitDao.put(VALID_FRUIT, PREVIOUS_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        storage.getFruits().clear();
    }

    @Test
    void doOperation_validData_isOk() {
        operationHandler.doOperation(VALID_FRUIT, VALID_QUANTITY);
        assertEquals(PREVIOUS_QUANTITY - VALID_QUANTITY, fruitDao.get(VALID_FRUIT));
    }

    @Test
    void doOperation_invalidFruit_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.doOperation(INVALID_FRUIT, VALID_QUANTITY));
    }

    @Test
    void doOperation_nullQuantity_isOk() {
        operationHandler.doOperation(VALID_FRUIT, NULL_QUANTITY);
        assertEquals(PREVIOUS_QUANTITY - NULL_QUANTITY, fruitDao.get(VALID_FRUIT));
    }

    @Test
    void doOperation_negativeResult_isNotOk() {
        assertThrows(RuntimeException.class,
                () -> operationHandler.doOperation(VALID_FRUIT, INVALID_QUANTITY));
    }
}
