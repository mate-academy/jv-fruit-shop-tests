package shop.service.action;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDao;
import shop.dao.FruitDaoImpl;
import shop.db.DataStorage;
import shop.model.Fruit;

public class DecreaseActionHandlerTest {
    private static FruitDao fruitDao;
    private static Fruit testFruit;
    private static ActionHandler handler;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        testFruit = new Fruit("apple", 10);
        handler = new DecreaseActionHandler(fruitDao);
    }

    @After
    public void afterAll() {
        DataStorage.storage.clear();
    }

    @Test
    public void decreaseActionHandler_update_ok() {
        fruitDao.add(testFruit);
        handler.update(testFruit.getName(), 1);
        Fruit after = fruitDao.get(testFruit.getName());
        assertEquals(9, after.getCount());
    }

    @Test(expected = RuntimeException.class)
    public void decreaseActionHandler_update_notOk() {
        fruitDao.add(testFruit);
        handler.update(testFruit.getName(), 12);
    }

    @Test(expected = RuntimeException.class)
    public void decreaseActionHandler_update_null() {
        fruitDao.add(testFruit);
        handler.update("test", 12);
    }
}
