package core.basesyntax.dao;

import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static FruitDaoImpl fruitDao;
    private static final String FRUIT_BANANA = "banana";
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_STRAWBERRY = "strawberry";

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
    }

    @Test
    public void getQuantity_fruitExist_Ok() {
        Storage.getFruitsStorage().put(FRUIT_BANANA, 50);
        int expected = 50;
        int actual = fruitDao.getQuantity(FRUIT_BANANA);
        Assert.assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void getQuantity_nullFruit_notOk() {
        fruitDao.getQuantity(null);
    }

    @Test (expected = RuntimeException.class)
    public void getQuantity_fruitNotExist_notOk() {
        fruitDao.getQuantity("qwerty");
    }

    @Test
    public void update_fruitExist_Ok() {
        createDataStorage();
        fruitDao.update(FRUIT_BANANA, 1);
        int expected = 1;
        int actual = Storage.getFruitsStorage().get(FRUIT_BANANA);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.getFruitsStorage().clear();
    }

    private void createDataStorage() {
        Storage.getFruitsStorage().put(FRUIT_BANANA, 230);
        Storage.getFruitsStorage().put(FRUIT_APPLE, 880);
        Storage.getFruitsStorage().put(FRUIT_STRAWBERRY, 150);
    }
}

