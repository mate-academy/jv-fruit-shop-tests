package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.HashSet;
import java.util.Set;

import org.junit.BeforeClass;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Fruit CHERRY = new Fruit("cherry");
    private static final Integer CHERRY_QUANTITY = 150;
    private static final Fruit BANANA = new Fruit("banana");
    private static final Integer BANANA_QUANTITY = 50;
    private static final Fruit BLUEBERRY = new Fruit("blueberry");
    private static final Integer BLUEBERRY_QUANTITY = 23;
    private static FruitDao fruitDao;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        fruitDao.update(CHERRY, CHERRY_QUANTITY);
        fruitDao.update(BANANA, BANANA_QUANTITY);
        fruitDao.update(BLUEBERRY, BLUEBERRY_QUANTITY);
    }

    @Test
    public void updateTest_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("cherry"), 150);
        expected.put(new Fruit("banana"), 50);
        expected.put(new Fruit("blueberry"), 23);

        Map<Fruit, Integer> actual = Storage.getFruits();
        assertEquals(expected, actual);
    }

    @Test
    public void getTest_Ok() {
        Optional<Integer> expected;
        Optional<Integer> actual;

        expected = Optional.of(150);
        actual = fruitDao.get(CHERRY);
        assertEquals(expected, actual);

        expected = Optional.of(50);
        actual = fruitDao.get(BANANA);
        assertEquals(expected, actual);

        expected = Optional.of(23);
        actual = fruitDao.get(BLUEBERRY);
        assertEquals(expected, actual);
    }

    @Test
    public void getFruitsTest_Ok() {
        Set<Fruit> expected = new HashSet<>();
        expected.add(CHERRY);
        expected.add(BANANA);
        expected.add(BLUEBERRY);

        Set<Fruit> actual = fruitDao.getFruits();
        assertEquals(expected, actual);
    }
}