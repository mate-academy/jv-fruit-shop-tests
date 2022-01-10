package core.basesyntax.service.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import org.junit.After;
import org.junit.Test;

public class BalanceStorageServiceImplTest {
    private StorageService storageService = new BalanceStorageServiceImpl(new FruitDaoImpl());

    @After
    public void setUp() {
        Storage.fruits.clear();
    }

    @Test
    public void checkBalance_Ok() {
        storageService.apply("apple", 500);
        String actualName = Storage.fruits.get(0).getName();
        int actualQuantity = Storage.fruits.get(0).getQuantity();
        String expectedName = "apple";
        int expectedQuantity = 500;
        assertEquals(expectedName, actualName);
        assertEquals(expectedQuantity, actualQuantity);
    }
}
