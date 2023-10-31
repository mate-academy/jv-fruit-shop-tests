package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.strategy.impl.BalanceHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceHandler(fruitDao);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void execute_valid_Ok() {
        operationHandler.execute("banana", 200);
        assertEquals(200, Storage.STORAGE.get("banana"));
    }
}
