package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static FruitDao fruitDao;
    private static FruitTransaction fruitTransaction;
    private static FruitOperationHandler fruitOperationHandler;

    @BeforeClass
    public static void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitTransaction = new FruitTransaction();
        fruitOperationHandler = new BalanceOperationHandler(fruitDao);
    }

    @Test
    public void balanceOperationHandler_Ok() {
        fruitDao.addFruit("apple",150);
        fruitDao.addFruit("banana",130);
        fruitTransaction.setFruit("chery");
        fruitTransaction.setQuantity(500);
        fruitOperationHandler.handle(fruitTransaction);
        Map<String, Integer> fruits = fruitDao.getAll();
        int expected = 3;
        int actual = fruits.size();
        assertEquals(expected,actual);
        assertTrue(fruits.containsKey("apple"));
        assertTrue(fruits.containsKey("banana"));
        assertTrue(fruits.containsKey("chery"));
        assertTrue(fruits.containsValue(150));
        assertTrue(fruits.containsValue(130));
        assertTrue(fruits.containsValue(500));
    }
}
