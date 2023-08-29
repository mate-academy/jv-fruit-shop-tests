package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final Fruit BANANA = new Fruit("banana", 5);
    private static final Fruit RETURNED_BANANA = new Fruit("banana", 15);
    private static final int EXPECTED_BALANCE = 20;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new ReturnOperationHandler();
        Storage.getFruits().clear();
    }

    @Test
    void operate_ok() {
        fruitDao.add(BANANA);
        operationHandler.operate(RETURNED_BANANA, fruitDao);
        assertEquals(EXPECTED_BALANCE,
                fruitDao.get(BANANA.getName()).getQuantity());
    }
}
