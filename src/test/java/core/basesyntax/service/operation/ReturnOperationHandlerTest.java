package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String VALID_FRUIT = "apple";
    private static final Integer VALID_QUANTITY = 40;
    private static final Integer RETURN_FRUIT = 10;
    private static final Integer UN_VALID_RETURN_FRUIT = -100;
    private static OperationHandler returnHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        fruitDao.add(VALID_FRUIT,VALID_QUANTITY);
        returnHandler = new ReturnOperationHandler(fruitDao);
    }

    @AfterAll
    static void afterAll() {
        Storage.storageFruit.clear();
    }

    @Test
    void processTransaction_returnOperation_ok() {
        returnHandler.processTransaction(VALID_FRUIT, RETURN_FRUIT);
        Integer expected = VALID_QUANTITY + RETURN_FRUIT;
        assertEquals(expected, fruitDao.get(VALID_FRUIT));
    }

    @Test
    void processTransaction_returnOperationWithUnValidReturnFruit_notOk() {
        assertThrows(RuntimeException.class, () ->
                returnHandler.processTransaction(VALID_FRUIT, UN_VALID_RETURN_FRUIT));
    }
}
