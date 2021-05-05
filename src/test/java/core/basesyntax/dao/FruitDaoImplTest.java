package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Fruit cherry = new Fruit("cherry");
    private static final Integer CHERRY_QUANTITY = 150;
    private static final Fruit banana = new Fruit("banana");
    private static final Integer BANANA_QUANTITY = 50;
    private static final Fruit blueberry = new Fruit("blueberry");
    private static final Integer BLUEBERRY_QUANTITY = 23;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        fruitDao.update(cherry, CHERRY_QUANTITY);
        fruitDao.update(banana, BANANA_QUANTITY);
        fruitDao.update(blueberry, BLUEBERRY_QUANTITY);
    }

    @Test
    public void updateStorageTest_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("cherry"), 150);
        expected.put(new Fruit("banana"), 50);
        expected.put(new Fruit("blueberry"), 23);

        Map<Fruit, Integer> actual = Storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    public void getQuantityFromStorageTest_Ok() {
        Optional<Integer> expected;
        Optional<Integer> actual;

        expected = Optional.of(150);
        actual = fruitDao.get(cherry);
        assertEquals(expected, actual);

        expected = Optional.of(50);
        actual = fruitDao.get(banana);
        assertEquals(expected, actual);

        expected = Optional.of(23);
        actual = fruitDao.get(blueberry);
        assertEquals(expected, actual);
    }

    @Test
    public void getAllFruitsFromStorageTest_Ok() {
        Set<Fruit> expected = new HashSet<>();
        expected.add(cherry);
        expected.add(banana);
        expected.add(blueberry);

        Set<Fruit> actual = fruitDao.getFruits();
        assertEquals(expected, actual);
    }
}
