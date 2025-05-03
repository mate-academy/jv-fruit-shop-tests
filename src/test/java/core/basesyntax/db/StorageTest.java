package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.model.Fruit;
import org.junit.Test;

public class StorageTest {
    private Storage storage = new Storage();
    private Fruit apple = new Fruit("apple");
    private Fruit peach = new Fruit("peach");

    @Test
    public void storage_getFruitQuantity_ok() {
        storage.setFruitQuantity(apple, 10);
        assertEquals(10, storage.getFruitQuantity(apple));
    }

    @Test
    public void storage_getFruitQuantity_fromEmptyStorage() {
        assertEquals(0, storage.getFruitQuantity(apple));
    }

    @Test
    public void storage_setFruitQuantity_ok() {
        storage.setFruitQuantity(apple, 5);
        assertEquals(5, storage.getFruitQuantity(apple));
    }

    @Test
    public void storage_setNegativeFruitQuantity() {
        assertThrows(IllegalArgumentException.class, () -> storage.setFruitQuantity(apple, -1));
    }

    @Test
    public void storage_getAllFruits_ok() {
        storage.setFruitQuantity(apple, 10);
        storage.setFruitQuantity(peach, 5);
        assertEquals(2, storage.getAllFruits().size());
    }
}
