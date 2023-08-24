package core.basesyntax.service.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String VALID_FRUIT = "banana";
    private static final Integer VALID_QUANTITY = 90;
    private static final Integer SUPPLY_FRUIT = 10;
    private static OperationHandler supplyHandler;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        supplyHandler = new SupplyOperationHandler(fruitDao);
    }

    @AfterAll
    static void afterAll() {
        Storage.storageFruit.clear();
    }

    @Test
    void processTransaction_supplyOperation_ok() {
        fruitDao.add(VALID_FRUIT,VALID_QUANTITY);
        supplyHandler.processTransaction(VALID_FRUIT, SUPPLY_FRUIT);
        Integer expected = VALID_QUANTITY + SUPPLY_FRUIT;
        assertEquals(expected, fruitDao.get(VALID_FRUIT));
    }
}
