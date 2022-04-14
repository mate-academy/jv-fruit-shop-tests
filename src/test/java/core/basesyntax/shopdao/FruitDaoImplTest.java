package core.basesyntax.shopdao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dbimitation.Storage;
import core.basesyntax.fruitsassortment.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Map<Fruit, Integer> expected = new HashMap<>();
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit APPLE = new Fruit("apple");

    @Before
    public void clear() {
        Storage.getFruits().clear();
        expected.clear();
    }

    @Test
    public void add_ValidData_Ok() {
        fruitDao.add(BANANA, 15);
        expected.put(BANANA, 15);
        assertEquals(expected, Storage.getFruits());
    }

    @Test
    public void get_ValidData_Ok() {
        Storage.getFruits().put(APPLE, 47);
        assertEquals(47, fruitDao.getAmountOfFruits(APPLE));
    }

    @Test
    public void getAll_ValidData_Ok() {
        Storage.getFruits().put(BANANA, 50);
        Storage.getFruits().put(APPLE, 80);
        expected.put(BANANA, 50);
        expected.put(APPLE, 80);
        assertEquals(expected, fruitDao.getAll());
    }
}
