package shop.service.action;

import static org.junit.Assert.assertEquals;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import shop.dao.FruitDao;
import shop.dao.FruitDaoImpl;
import shop.db.DataStorage;
import shop.model.Fruit;

public class IncreaseActionHandlerTest {
    private static FruitDao fruitDao;
    private static Fruit testFruit;
    private static ActionHandler handler;

    @BeforeClass
    public static void beforeAll() {
        fruitDao = new FruitDaoImpl();
        testFruit = new Fruit("apple", 10);
        handler = new IncreaseActionHandler(fruitDao);
    }

    @After
    public void afterAll() {
        DataStorage.storage.clear();
    }

    @Test
    public void increaseActionHandler_update_ok() {
        fruitDao.add(testFruit);
        handler.update(testFruit.getName(), 1);
        Fruit after = fruitDao.get(testFruit.getName());
        assertEquals(11, after.getCount());
    }

    @Test
    public void increaseActionHandler_update_withoutAdd_Ok() {
        handler.update("banana", 12);
        assertEquals(12, fruitDao.get("banana").getCount());
    }
}
