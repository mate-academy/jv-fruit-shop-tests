package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static FruitDao fruitDao;
    private static BalanceOperationHandler balanceOperationHandler;
    private Fruit apple;

    @BeforeAll
    static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        balanceOperationHandler = new BalanceOperationHandler(fruitDao);
    }

    @BeforeEach
    void setUp() {
        apple = new Fruit();
        apple.setTypeFruit("apple");
        apple.setTypeOperation(Fruit.Operation.BALANCE);
    }

    @Test
    void fruitOperation_invalidQuantity_notOk() {
        apple.setQuantity(-5);

        assertThrows(RuntimeException.class, () -> {
            balanceOperationHandler.fruitOperation(apple);
        }, "An exception should be thrown");
    }

    @Test
    void fruitOperation_validQuantity_ok() {
        apple.setQuantity(100);
        balanceOperationHandler.fruitOperation(apple);

        assertEquals(apple.getQuantity(), fruitDao.get(apple.getTypeFruit()),
                "Data on the fruit: " + apple
                        + ", was not entered. Please check the data.");
    }
}
