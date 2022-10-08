package core.basesyntax.dao.impl;

import static org.junit.Assert.fail;

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
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Before
    public void setUp() {
        Storage.fruits.put(BANANA, STARTED_VALUE);
        Storage.fruits.put(PINEAPPLE, STARTED_VALUE);
    }

    @Test
    public void nullFruit_NotOk() {
        try {
            fruitDao.update(null, STARTED_VALUE);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown if fruit is null");
    }

    @Test
    public void update_negativeOperationValue_Ok() {
        fruitDao.update(APPLE, NEGATIVE_VALUE);
        Integer actual = Storage.fruits.get(APPLE);
        Assert.assertTrue(actual > 0);
    }

    @Test
    public void updateFruitValue_Ok() {
        fruitDao.update(BANANA, CHANGED_VALUE);
        fruitDao.update(PINEAPPLE, CHANGED_VALUE);
        Integer actualBananaValue = Storage.fruits.get(BANANA);
        Integer actualPineappleValue = Storage.fruits.get(PINEAPPLE);
        Assert.assertEquals("Bananas amount should be "
                        + 33 + " but actual: " + actualBananaValue,
                33, (int) actualBananaValue);
        Assert.assertEquals("Pineapples amount should be "
                        + 33 + " but actual: " + actualPineappleValue,
                33, (int) actualPineappleValue);
    }

    @Test
    public void getQuantity_Ok() {
        Integer actual = fruitDao.getQuantity(BANANA);
        Integer expected = Storage.fruits.get(BANANA);
        Assert.assertSame(actual, expected);
    }

    @Test
    public void getQuantity_wrongKey_NotOk() {
        try {
            fruitDao.getQuantity(APPLE);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown when key is not present in storage");
    }

    @Test
    public void getQuantity_nullValue_NotOk() {
        Storage.fruits.put(APPLE, null);
        try {
            fruitDao.getQuantity(APPLE);
        } catch (RuntimeException e) {
            return;
        }
        fail("Runtime exception should be thrown when quantity is null");
    }

    @Test
    public void getAll_Ok() {
        Map<Fruit, Integer> actual = fruitDao.getAll();
        Map<Fruit, Integer> expected = Storage.fruits;
        int actualSize = actual.size();
        int expectedSize = 2;
        Assert.assertEquals(expected, actual);
        Assert.assertEquals("Storage size not as expected",
                expectedSize, actualSize);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
