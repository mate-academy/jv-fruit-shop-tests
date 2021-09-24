package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.models.Fruit;
import core.basesyntax.models.FruitRecord;
import core.basesyntax.storage.Storage;
import java.util.Set;
import org.junit.After;
import org.junit.Test;

public class FruitDaoImpTest {
    private static Storage storage = new Storage();
    private FruitDao fruitDao = new FruitDaoImp(storage);
    private FruitRecord bananaRecord
            = new FruitRecord('s', "banana", 23);
    private FruitRecord appleRecord
            = new FruitRecord('s', "apple", 24);
    private Fruit apple = new Fruit("apple", 24);

    @Test(expected = NullPointerException.class)
    public void passNullToConstructor_Exception() {
        Storage storage = null;
        new FruitDaoImp(storage);
    }

    @Test
    public void validConstructor_Ok() {
        Storage storage = new Storage();
        new FruitDaoImp(storage);
    }

    @Test(expected = NullPointerException.class)
    public void put_passNull_Exception() {
        FruitRecord fruitRecord = null;
        FruitDaoImp fruitDaoServiceImp = new FruitDaoImp(new Storage());
        fruitDaoServiceImp.put(fruitRecord);
    }

    @Test()
    public void put_Ok() {
        int expected = 1;
        fruitDao.save(bananaRecord);
        fruitDao.put(bananaRecord);
        int actual = fruitDao.get().size();
        assertEquals(expected,actual);
    }

    @Test
    public void put_NotAddingNewDataInStorage_Ok() {
        fruitDao.put(appleRecord);
        assertEquals(storage.getFruitsInStorage().contains(apple), false);
    }

    @Test(expected = NullPointerException.class)
    public void save_NullRecord_Exception() {
        FruitRecord fruitRecord = null;
        fruitDao.save(fruitRecord);
    }

    @Test
    public void save_Ok() {
        fruitDao.save(bananaRecord);
        assertEquals(fruitDao.get().size(), 1);
    }

    @Test
    public void get_EmptyStorage_Ok() {
        Set<Fruit> fruits = fruitDao.get();
        assertEquals(fruits.size(), 0);
    }

    @Test
    public void get_ValidData_Ok() {
        fruitDao.save(appleRecord);
        Set<Fruit> fruits = fruitDao.get();
        assertEquals(fruits.size(), 1);
    }

    @After
    public void afterEachTest() {
        storage = new Storage();
        fruitDao = new FruitDaoImp(storage);
    }
}
