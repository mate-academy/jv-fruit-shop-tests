package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.After;
import org.junit.Test;

public class PurchaseStorageServiceImplTest {
    private StorageService storageService = new PurchaseStorageServiceImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();        
    }

    @Test
    public void checkPurchase_Ok() {
        Fruit fruit = new Fruit();
        fruit.setName("orange");
        fruit.setQuantity(500);
        Storage.fruits.add(fruit);
        storageService.apply("orange", 150);
        int actual = Storage.fruits.get(0).getQuantity();
        int expected = 350;
        assertEquals(expected, actual);
    }
}
