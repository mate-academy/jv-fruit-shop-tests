package core.basesyntax.shopdao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dbimitation.Storage;
import core.basesyntax.fruitsassortment.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Map<Fruit, Integer> expected = new HashMap<>();
    private static final FruitDao fruitDao = new FruitDaoImpl();
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit APPLE = new Fruit("apple");

    @Test
    public void add_ValidData_Ok() {
        Storage.getFruits().clear();
        expected.put(BANANA, 15);
        fruitDao.add(BANANA, 15);
        assertEquals(expected, Storage.getFruits());
    }

    @Test
    public void get_ValidData_Ok() {
        Storage.getFruits().clear();
        Storage.getFruits().put(APPLE, 47);
        assertEquals((int) Storage.getFruits().get(APPLE), fruitDao.getAmountOfFruits(APPLE));
    }

    @Test
    public void getAll_ValidData_Ok() {
        Storage.getFruits().clear();
        Storage.getFruits().put(BANANA, 50);
        Storage.getFruits().put(APPLE, 80);
        assertEquals(Storage.getFruits(), fruitDao.getAll());
    }

}
