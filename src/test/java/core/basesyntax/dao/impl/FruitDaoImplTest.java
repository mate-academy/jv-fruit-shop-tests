package core.basesyntax.dao.impl;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class FruitDaoImplTest {
    public static final String FRUIT_NAME = "banana";
    private static Fruit fruit;
    private static FruitDao fruitDao;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        fruit = new Fruit(FRUIT_NAME);
    }

    @Test
    public void add_fruit_ok() {
        fruitDao.add(fruit);
        assertTrue(Storage.getFruits().contains(fruit));
    }

    @Test
    public void get_fruit_ok() {
        Storage.getFruits().add(fruit);
        assertEquals(fruit, fruitDao.get(fruit.getName()));
    }

    @Test
    public void get_fruitNullName_notOk() {
        thrown.expect(RuntimeException.class);
        fruitDao.get(null);
    }

    @Test
    public void getAll_fruit_ok() {
        assertEquals(Storage.getFruits(), fruitDao.getAll());
    }

    @AfterClass
    public static void afterClass() {
        Storage.getFruits().remove(fruit);
    }
}
