package core.basesyntax.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String VALID_FRUIT = "banana";
    private static final String INVALID_FRUIT = "milk";
    private static final int PREVIOUS_QUANTITY = 30;
    private static final int VALID_QUANTITY = 20;
    private static final int NULL_QUANTITY = 0;
    private final Storage storage = new Storage();
    private final FruitDao fruitDao = new FruitDaoImpl(storage);
    private final OperationHandler operationHandler = new ReturnOperationHandler(fruitDao);

    @BeforeEach
    void tearUp() {
        fruitDao.put(VALID_FRUIT, PREVIOUS_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        storage.getFruits().clear();
    }

    @Test
    void doOperation_validData_isOk() {
        operationHandler.doOperation(VALID_FRUIT, VALID_QUANTITY);
        assertEquals(PREVIOUS_QUANTITY + VALID_QUANTITY, fruitDao.get(VALID_FRUIT));
    }

    @Test
    void doOperation_invalidFruit_isOk() {
        operationHandler.doOperation(INVALID_FRUIT, VALID_QUANTITY);
        assertEquals(VALID_QUANTITY, fruitDao.get(INVALID_FRUIT));
    }

    @Test
    void doOperation_nullQuantity_isOk() {
        operationHandler.doOperation(VALID_FRUIT, NULL_QUANTITY);
        assertEquals(PREVIOUS_QUANTITY + NULL_QUANTITY, fruitDao.get(VALID_FRUIT));
    }
}
