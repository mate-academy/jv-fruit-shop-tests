package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.shop.item.Fruit;
import org.junit.Test;

public class FruitDaoTest {

    private static final Fruit banana = new Fruit("banana", 0);
    private static final Fruit thirtyBananas = new Fruit("banana", 30);

    @Test
    public void testAddFruitDaoImpl() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(thirtyBananas);
        Fruit actual = Storage.fruits.get(0);
        assertEquals(thirtyBananas, actual);
    }

    @Test
    public void testGetFruitDaoImpl() {
        FruitDao fruitDao = new FruitDaoImpl();
        Storage.fruits.add(thirtyBananas);
        assertEquals(thirtyBananas, fruitDao.get(thirtyBananas));
    }

    @Test
    public void testUpdateFruitDaoImpl() {
        Storage.fruits.clear();
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.add(banana);
        fruitDao.update(thirtyBananas);
        Integer expected = 30;
        assertEquals("Method UPDATE in class FruitDaoImpl no work",
                expected,
                fruitDao.getAll().get(0).getQuality());
    }

}
