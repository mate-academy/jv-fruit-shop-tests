package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String APPLE = "Apple";
    private static final String BANANA = "Banana";

    @Test
    public void setFruits() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put(APPLE, 10);
        fruits.put(BANANA, 5);
        Storage storage = new Storage();
        storage.setFruits(fruits);
        assertEquals(fruits, storage.getFruits());
    }

    @Test
    public void getFruits() {
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put(APPLE, 10);
        fruits.put(BANANA, 5);
        Storage storage = new Storage();
        storage.setFruits(fruits);
        Map<String, Integer> restoredFruits = storage.getFruits();
        assertEquals(fruits, restoredFruits);
    }

    @Test
    public void getFruitQuantity_nullFruitQuantity() {
        assertEquals(0, Storage.getFruitQuantity(null));
    }

    @Test
    public void addQuantity_quantityIsZero() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                APPLE, 0);
        Storage.addQuantity(fruitTransaction);
        Storage.quantities.put(APPLE, 0);
        assertEquals(0, Storage.getFruitQuantity(APPLE));
    }

    @Test
    public void addQuantity_quantityIsNegative() {
        FruitTransaction fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                APPLE, -10);
        Storage.quantities.put(APPLE, -10);
        assertEquals(-10, Storage.quantities.get(APPLE));
    }

    @Test
    public void getFruitQuantity_EmptyData() {
        assertEquals(0, Storage.getFruitQuantity(""));
    }
}
