package core.basesyntax.dao;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    public static final String FRUIT_TYPE_APPLE = "appleFruit";
    private static Fruit fruitApple;
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        fruitDao = new FruitDaoImpl();
        fruitApple = new Fruit(FRUIT_TYPE_APPLE);
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.getFruits().remove(fruitApple);
    }

    @Test
    public void addFruit_Ok() {
        fruitDao.add(fruitApple);
        assertTrue(FruitsStorage.getFruits().contains(fruitApple));
    }

    @Test
    public void getFruit_ok() {
        FruitsStorage.getFruits().add(fruitApple);
        assertEquals(fruitApple, fruitDao.get(fruitApple.getFruitType()));
    }

    @Test
    public void getNullNameFruit_notOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.get(null));
    }

    @Test
    public void getAll() {
        assertEquals(FruitsStorage.getFruits(), fruitDao.getAll());
    }
}
