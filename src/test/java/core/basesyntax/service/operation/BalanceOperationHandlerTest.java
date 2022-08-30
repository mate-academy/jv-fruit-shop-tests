package core.basesyntax.service.operation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    @Test
    public void validBalanceOperationHandler_Ok() {
        FruitDao fruitDao = new FruitDaoImpl();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitDao.addFruit("apple",150);
        fruitDao.addFruit("banana",130);
        FruitOperationHandler fruitOperationHandler = new BalanceOperationHandler(fruitDao);
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
