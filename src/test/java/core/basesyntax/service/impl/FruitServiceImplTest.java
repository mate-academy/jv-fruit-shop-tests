package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Test;

public class FruitServiceImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(fruitDao);

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }

    @Test
    public void getQuantity_Ok() {
        fruitDao.update("apple", 17);
        int actual = fruitService.getQuantity("apple");
        assertEquals(17, actual);
    }

    @Test
    public void getAll_Ok() {
        fruitDao.update("banana", 6);
        fruitDao.update("apple", 14);
        List<Fruit> expected = new ArrayList<>();
        Fruit banana = new Fruit("banana", 6);
        Fruit apple = new Fruit("apple", 14);
        expected.add(banana);
        expected.add(apple);
        List<Fruit> actual = fruitService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void update_Ok() {
        fruitDao.update("banana", 6);
        fruitService.update("banana", 10);
        int actual = fruitDao.getQuantity("banana");
        assertEquals(10, actual);
    }

    @Test(expected = RuntimeException.class)
    public void getNonExisting_NotOk() {
        fruitDao.update("banana", 4);
        fruitService.getQuantity("apple");
    }

    @Test(expected = RuntimeException.class)
    public void getNullInput_NotOk() {
        fruitDao.update("pear", 9);
        fruitService.getQuantity(null);
    }

    @Test(expected = RuntimeException.class)
    public void updateNull_NotOk() {
        fruitService.update("apple", null);
        fruitService.update(null, 6);
        fruitService.update(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void updateNegative_NotOk() {
        fruitService.update("apple", -12);
    }
}
