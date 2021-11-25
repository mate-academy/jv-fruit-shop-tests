package shop.service.action;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDaoImpl;
import shop.db.DataStorage;
import shop.model.Fruit;

public class IncreaseActionHandlerTest {
    private static FruitDaoImpl fruitDao;
    private static Fruit test_fruit;
    private static ActionHandler handler;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        test_fruit = new Fruit("apple", 10);
        handler = new IncreaseActionHandler();
    }

    @AfterClass
    public static void afterAll() {
        DataStorage.storage.clear();
    }

    @Test
    public void increaseActionHandler_update_ok() {
        fruitDao.add(test_fruit);
        handler.update(test_fruit.getName(), 1);
        Fruit after = fruitDao.get(test_fruit.getName());
        assertEquals(11, after.getCount());
    }

    @Test
    public void increaseActionHandler_update_withoutAdd_Ok() {
        handler.update("banana", 12);
        assertEquals(12, fruitDao.get("banana").getCount());
    }
}
