package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final Fruit FRUIT = new Fruit("banana", 67);
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new BalanceOperationHandler();
        Storage.getFruits().clear();
    }

    @Test
    void operate_ok() {
        operationHandler.operate(FRUIT, fruitDao);
        assertEquals(FRUIT.getQuantity(),
                fruitDao.get(FRUIT.getName()).getQuantity());
    }
}
