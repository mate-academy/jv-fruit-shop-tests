package core.basesyntax.model.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.model.dto.Fruit;
import core.basesyntax.model.dto.FruitRecordDto;
import core.basesyntax.storage.FruitStorage;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitDaoImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @Before
    public void setUp() {
        FruitStorage.getFruits().put(new Fruit("orange"), 25);
        FruitStorage.getFruits().put(new Fruit("apple"), 15);
        FruitStorage.getFruits().put(new Fruit("banana"), 55);
    }

    @Test
    public void update_Ok() {
        FruitRecordDto fruitRecord = new FruitRecordDto(Operation.RETURN, "orange", 10);
        fruitDao.update(fruitRecord, 100);
        int expected = 100;
        int actual = fruitDao.getCurrentQuantity(fruitRecord);
        assertEquals(expected, actual);
    }

    @Test
    public void getAll_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("orange"), 25);
        expected.put(new Fruit("apple"), 15);
        expected.put(new Fruit("banana"), 55);
        Map<Fruit, Integer> actual = fruitDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void getCurrentQuantity_existingKey_Ok() {
        FruitRecordDto fruitRecord = new FruitRecordDto(Operation.RETURN, "orange", 10);
        int expected = 25;
        int actual = fruitDao.getCurrentQuantity(fruitRecord);
        assertEquals(expected, actual);
    }

    @Test
    public void getCurrentQuantity_nonExistingKey_Ok() {
        FruitRecordDto fruitRecord = new FruitRecordDto(Operation.RETURN, "qiwi", 10);
        int expected = 0;
        int actual = fruitDao.getCurrentQuantity(fruitRecord);
        assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        FruitStorage.getFruits().clear();
    }
}
