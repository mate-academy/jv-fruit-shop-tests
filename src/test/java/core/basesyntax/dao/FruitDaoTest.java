package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.shop.item.Fruit;
import org.junit.Test;

public class FruitDaoTest {

    private static final Fruit BANANA0 = new Fruit("banana", 0);
    private static final Fruit BANANA30 = new Fruit("banana", 30);

    @Test
    public void testAddFruitDaoImpl() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(BANANA30);
        Fruit actual = Storage.fruits.get(0);
        assertEquals(BANANA30, actual);
    }

    @Test
    public void testGetFruitDaoImpl() {
        FruitDao fruitDao = new FruitDaoImpl();
        Storage.fruits.add(BANANA30);
        assertEquals(BANANA30, fruitDao.get(BANANA30));
    }

    @Test
    public void testUpdateFruitDaoImpl() {
        Storage.fruits.clear();
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(BANANA0);
        fruitDao.update(BANANA30);
        Integer expected = 30;
        assertEquals("Method UPDATE in class FruitDaoImpl no work",
                expected,
                fruitDao.getAll().get(0).getQuality());
    }

}
