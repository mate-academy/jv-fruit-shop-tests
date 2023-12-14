package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static FruitDao fruitDao;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private Fruit apple;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        purchaseOperationHandler = new PurchaseOperationHandler(fruitDao);
    }

    @BeforeEach
    void setUp() {
        apple = new Fruit();
        apple.setTypeFruit("apple");
        apple.setTypeOperation(Fruit.Operation.PURCHASE);
    }

    @Test
    void fruitOperation_invalidQuantity_notOk() {
        apple.setQuantity(15);

        fruitDao.add("apple", 10);

        assertThrows(RuntimeException.class, () -> {
            purchaseOperationHandler.fruitOperation(apple);
        }, "An exception should be thrown");
    }

    @Test
    void fruitOperation_validQuantity_ok() {
        apple.setQuantity(5);

        fruitDao.add("apple", 10);

        purchaseOperationHandler.fruitOperation(apple);

        assertEquals(5, fruitDao.get(apple.getTypeFruit()),
                "Incorrectly entered data. Please check the data");
    }
}
