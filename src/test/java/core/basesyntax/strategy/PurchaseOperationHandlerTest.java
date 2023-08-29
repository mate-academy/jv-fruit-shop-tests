package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final Fruit STORAGE_FRUIT = new Fruit("banana", 67);
    private static final Fruit WRONG_PURCHASED_FRUIT = new Fruit("banana", 68);
    private static final Fruit CORRECT_PURCHASED_FRUIT = new Fruit("banana", 66);
    private static final int EXPECTED_BALANCE = 1;
    private static FruitDao fruitDao;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperationHandler();
        Storage.getFruits().clear();
    }

    @Test
    void operate_notOk() {
        assertThrows(RuntimeException.class, () ->
                operationHandler.operate(WRONG_PURCHASED_FRUIT, fruitDao));
    }

    @Test
    void operate_ok() {
        operationHandler.operate(CORRECT_PURCHASED_FRUIT, fruitDao);
        assertEquals(EXPECTED_BALANCE,
                fruitDao.get(CORRECT_PURCHASED_FRUIT.getName()).getQuantity());
    }

    @BeforeEach
    void setUp() {
        Storage.getFruits().add(STORAGE_FRUIT);
    }

    @AfterEach
    void clearStorage() {
        Storage.getFruits().clear();
    }
}
