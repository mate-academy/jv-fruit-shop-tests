package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String KIWI = "kiwi";
    private static final String ORANGE = "orange";

    @BeforeEach
    void setUp() {
        Storage.clearStorage();
    }

    @Test
    void addFruit_positiveQuantity_ok() {
        Storage.addFruit(APPLE, 10);
        assertEquals(10, Storage.getFruitQuantity(APPLE));

        Storage.addFruit(APPLE, 25);
        assertEquals(35, Storage.getFruitQuantity(APPLE));
    }

    @Test
    void addFruit_existingFruit_ok() {
        Storage.addFruit(APPLE, 10);
        Storage.addFruit(APPLE, 5);
        assertEquals(15, Storage.getFruitQuantity(APPLE));
    }

    @Test
    void addFruit_addNewFruit_ok() {
        Storage.addFruit(ORANGE, 10);
        assertEquals(10, Storage.getFruitQuantity(ORANGE));
    }

    @Test
    void getFruitStorage_notEmpty_ok() {
        Storage.addFruit(APPLE, 10);
        Storage.addFruit(BANANA, 20);
        Map<String, Integer> fruitStorage = Storage.getFruitStorage();
        assertEquals(2, fruitStorage.size());
        assertEquals(10, fruitStorage.get(APPLE));
        assertEquals(20, fruitStorage.get(BANANA));
    }

    @Test
    void getFruitQuantity_nonExistingFruit_ok() {
        assertEquals(0, Storage.getFruitQuantity(KIWI));
    }
}

