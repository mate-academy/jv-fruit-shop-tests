package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String VALID_FRUIT = "banana";
    private static final String NEW_FRUIT = "ban";
    private static final Integer VALID_QUANTITY = 90;
    private static final Integer SUPPLY_FRUIT = 10;
    private static final Integer UN_VALID_SUPPLY_FRUIT = -10;
    private static OperationHandler supplyHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        fruitDao.add(VALID_FRUIT, VALID_QUANTITY);
        supplyHandler = new SupplyOperationHandler(fruitDao);
    }

    @AfterAll
    static void afterAll() {
        Storage.storageFruit.clear();
    }

    @Test
    void processTransaction_supplyOperation_ok() {
        supplyHandler.processTransaction(VALID_FRUIT, SUPPLY_FRUIT);
        Integer expected = VALID_QUANTITY + SUPPLY_FRUIT;
        assertEquals(expected, fruitDao.get(VALID_FRUIT));
    }

    @Test
    void processTransaction_supplyOperationWithNotValidSupplyQuantity_notOk() {
        assertThrows(RuntimeException.class, () ->
                supplyHandler.processTransaction(VALID_FRUIT, UN_VALID_SUPPLY_FRUIT));
    }

    @Test
    void processTransaction_supplyOperationWithNewFruit_ok() {
        supplyHandler.processTransaction(NEW_FRUIT, VALID_QUANTITY);
        assertDoesNotThrow(() -> NullPointerException.class);
    }
}
