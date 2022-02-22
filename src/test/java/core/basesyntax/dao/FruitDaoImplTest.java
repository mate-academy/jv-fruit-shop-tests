package core.basesyntax.dao;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertThrows;

import core.basesyntax.db.FruitsStorage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    public static final String FRUIT_TYPE_APPLE = "appleFruit";
    private static Fruit fruitApple;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitDao = new FruitDaoImpl();
        fruitApple = new Fruit(FRUIT_TYPE_APPLE);
    }

    @Test
    public void add_fruit_ok() {
        fruitDao.add(fruitApple);
        assertTrue(FruitsStorage.getFruits().contains(fruitApple));
    }

    @Test
    public void get_fruit_ok() {
        FruitsStorage.getFruits().add(fruitApple);
        assertEquals(fruitApple, fruitDao.get(fruitApple.getFruitType()));
    }

    @Test
    public void get_fruitNullName_notOk() {
        assertThrows(RuntimeException.class, () -> fruitDao.get(null));
    }

    @Test
    public void getAll_fruits_ok() {
        assertEquals(FruitsStorage.getFruits(), fruitDao.getAll());
    }

    @AfterClass
    public static void afterClass() {
        FruitsStorage.getFruits().remove(fruitApple);
    }
}
