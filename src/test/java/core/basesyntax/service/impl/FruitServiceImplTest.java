package core.basesyntax.service.impl;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.FruitService;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FruitServiceImplTest {
    private final StorageDao storageDao = new StorageDaoImpl();
    private final FruitService fruitService = new FruitServiceImpl(storageDao);

    @Test
    public void getAmount_Ok() {
        Storage.fruitsStorage.put("banana", 28);
        int actual = fruitService.getAmount("banana");
        assertEquals(28, actual);
    }

    @Test
    public void getAll_Ok() {
        Storage.fruitsStorage.put("orange", 76);
        Storage.fruitsStorage.put("apple", 44);
        List<Fruit> expected = new ArrayList<>();
        Fruit banana = new Fruit("orange", 76);
        Fruit apple = new Fruit("apple", 44);
        expected.add(banana);
        expected.add(apple);
        List<Fruit> actual = fruitService.getAll();
        assertEquals(expected, actual);
    }

    @Test
    public void update_Ok() {
        Storage.fruitsStorage.put("apple", 50);
        fruitService.update("banana", 40);
        int actual = Storage.fruitsStorage.get("banana");
        assertEquals(40, actual);
    }

    @Test(expected = RuntimeException.class)
    public void get_NonExisting_NotOk() {
        Storage.fruitsStorage.put("orange", 60);
        fruitService.getAmount("banana");
    }

    @Test(expected = RuntimeException.class)
    public void get_NullInput_NotOk() {
        Storage.fruitsStorage.put("pineapple", 75);
        fruitService.getAmount(null);
    }

    @Test(expected = RuntimeException.class)
    public void update_Null_NotOk() {
        fruitService.update("orange", null);
        fruitService.update(null, 50);
        fruitService.update(null, null);
    }

    @Test(expected = RuntimeException.class)
    public void update_Negative_NotOk() {
        fruitService.update("banana", -1);
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
