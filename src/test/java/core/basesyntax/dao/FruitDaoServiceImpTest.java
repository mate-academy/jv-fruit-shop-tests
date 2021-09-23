package core.basesyntax.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.models.Fruit;
import core.basesyntax.models.FruitRecord;
import core.basesyntax.storage.Storage;
import java.util.Set;
import org.junit.After;
import org.junit.Test;

public class FruitDaoServiceImpTest {
    private static Storage storage = new Storage();
    private FruitDaoService fruitDaoService = new FruitDaoServiceImp(storage);
    private FruitRecord bananaRecord
            = new FruitRecord('s', "banana", 23);
    private FruitRecord appleRecord
            = new FruitRecord('s', "apple", 24);
    private Fruit apple = new Fruit("apple", 24);

    @Test(expected = NullPointerException.class)
    public void passNullToConstructor_Exception() {
        Storage storage = null;
        new FruitDaoServiceImp(storage);
    }

    @Test
    public void validConstructor_Ok() {
        Storage storage = new Storage();
        new FruitDaoServiceImp(storage);
    }

    @Test(expected = NullPointerException.class)
    public void put_passNull_Exception() {
        FruitRecord fruitRecord = null;
        FruitDaoServiceImp fruitDaoServiceImp = new FruitDaoServiceImp(new Storage());
        fruitDaoServiceImp.put(fruitRecord);
    }

    @Test()
    public void put_Ok() {
        int expected = 1;
        fruitDaoService.save(bananaRecord);
        fruitDaoService.put(bananaRecord);
        int actual = fruitDaoService.get().size();
        assertEquals(expected,actual);
    }

    @Test
    public void put_NotAddingNewDataInStorage_Ok() {
        fruitDaoService.put(appleRecord);
        assertEquals(storage.getFruitsInStorage().contains(apple), false);
    }

    @Test(expected = NullPointerException.class)
    public void save_NullRecord_Exception() {
        FruitRecord fruitRecord = null;
        fruitDaoService.save(fruitRecord);
    }

    @Test
    public void save_Ok() {
        fruitDaoService.save(bananaRecord);
        assertEquals(fruitDaoService.get().size(), 1);
    }

    @Test
    public void get_EmptyStorage_Ok() {
        Set<Fruit> fruits = fruitDaoService.get();
        assertEquals(fruits.size(), 0);
    }

    @Test
    public void get_ValidData_Ok() {
        fruitDaoService.save(appleRecord);
        Set<Fruit> fruits = fruitDaoService.get();
        assertEquals(fruits.size(), 1);
    }

    @After
    public void afterEachTest() {
        storage = new Storage();
        fruitDaoService = new FruitDaoServiceImp(storage);
    }
}
