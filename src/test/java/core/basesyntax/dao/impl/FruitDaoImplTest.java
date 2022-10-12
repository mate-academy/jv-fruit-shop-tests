package core.basesyntax.dao.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private static final Integer STARTED_VALUE = 20;
    private static final Integer NEGATIVE_VALUE = -20;
    private static final Integer CHANGED_VALUE = 33;
    private static final Fruit BANANA = new Fruit("banana");
    private static final Fruit PINEAPPLE = new Fruit("pineapple");
    private static final Fruit APPLE = new Fruit("apple");
    private FruitDao fruitDao;

    @Before
    public void setUp() {
        Storage.fruits.put(BANANA, STARTED_VALUE);
        Storage.fruits.put(PINEAPPLE, STARTED_VALUE);
        fruitDao = new FruitDaoImpl();
    }

    @Test(expected = RuntimeException.class)
    public void nullFruit_NotOk() {
        fruitDao.update(null, STARTED_VALUE);
    }

    @Test
    public void update_negativeOperationValue_Ok() {
        fruitDao.update(APPLE, NEGATIVE_VALUE);
        Integer actual = Storage.fruits.get(APPLE);
        Assert.assertTrue(actual > 0);
    }

    @Test
    public void update_correctValue_Ok() {
        fruitDao.update(BANANA, CHANGED_VALUE);
        Integer actualBananaValue = Storage.fruits.get(BANANA);
        Assert.assertEquals("Bananas amount should be "
                        + 33 + " but actual: " + actualBananaValue,
                33, (int) actualBananaValue);
    }

    @Test
    public void getQuantity_Ok() {
        Integer actual = fruitDao.getQuantity(BANANA);
        Integer expected = Storage.fruits.get(BANANA);
        Assert.assertSame(actual, expected);
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_wrongKey_NotOk() {
        fruitDao.getQuantity(APPLE);
    }

    @Test(expected = RuntimeException.class)
    public void getQuantity_nullValue_NotOk() {
        Storage.fruits.put(APPLE, null);
        fruitDao.getQuantity(APPLE);
    }

    @Test
    public void getAll_Ok() {
        Map<Fruit, Integer> actual = fruitDao.getAll();
        Map<Fruit, Integer> expected = Storage.fruits;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
