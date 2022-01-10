package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Test;

public class SupplyStorageServiceImplTest {
    private StorageService storageService = new SupplyStorageServiceImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void checkSupply_Ok() {
        Fruit fruit = new Fruit();
        fruit.setName("apple");
        fruit.setQuantity(20);
        Storage.fruits.add(fruit);
        storageService.apply("apple", 20);
        int expected = 40;
        int actual = Storage.fruits.get(0).getQuantity();
        assertEquals(expected, actual);

    }
}
