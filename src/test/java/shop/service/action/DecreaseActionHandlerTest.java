package shop.service.action;

import static org.junit.Assert.assertEquals;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDaoImpl;
import shop.db.DataStorage;
import shop.model.Fruit;

public class DecreaseActionHandlerTest {
    private static FruitDaoImpl fruitDao;
    private static Fruit test_fruit;
    private static ActionHandler handler;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        test_fruit = new Fruit("apple", 10);
        handler = new DecreaseActionHandler();
    }

    @AfterClass
    public static void afterAll() {
        DataStorage.storage.clear();
    }

    @Test
    public void decreaseActionHandler_update_ok() {
        fruitDao.add(test_fruit);
        handler.update(test_fruit.getName(), 1);
        Fruit after = fruitDao.get(test_fruit.getName());
        assertEquals(9, after.getCount());
    }

    @Test(expected = RuntimeException.class)
    public void decreaseActionHandler_update_notOk() {
        fruitDao.add(test_fruit);
        handler.update(test_fruit.getName(), 12);
    }

    @Test(expected = RuntimeException.class)
    public void decreaseActionHandler_update_null() {
        fruitDao.add(test_fruit);
        handler.update("test", 12);
    }
}
