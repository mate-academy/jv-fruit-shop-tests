package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.impl.SupplyHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyHandler(fruitDao);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void execute_valid_Ok() {
        Storage.STORAGE.put("banana", 100);
        operationHandler.execute("banana", 40);
        assertEquals(140, Storage.STORAGE.get("banana"));
    }
}
