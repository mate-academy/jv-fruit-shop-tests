package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Test;

public class ReturnStorageServiceImplTest {
    private StorageService storageService = new ReturnStorageServiceImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void checkReturn_Ok() {
        Fruit fruit = new Fruit();
        fruit.setName("apple");
        fruit.setQuantity(100);
        Storage.fruits.add(fruit);
        storageService.apply("apple", 100);
        int actual = Storage.fruits.get(0).getQuantity();
        int expected = 200;
        assertEquals(expected, actual);
    }
}
