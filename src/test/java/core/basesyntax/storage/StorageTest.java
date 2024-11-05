package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";

    private static final int QUANTITY_10 = 10;
    private static final int QUANTITY_5 = 5;
    private static final int QUANTITY_15 = 15;
    private static final int QUANTITY_0 = 0;
    private static final int QUANTITY_NEGATIVE_5 = -5;
    private static final int QUANTITY_NEGATIVE_10 = -10;
    private static final int QUANTITY_20 = 20;
    private static final int EXPECTED_MAP_SIZE_2 = 2;
    private FruitStorage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
    }

    @Test
    void addFruit_newFruit_addsFruitSuccessfully() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        assertEquals(QUANTITY_10, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void addFruit_existingFruit_increasesQuantity() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        storage.addFruit(FRUIT_APPLE, QUANTITY_5);
        assertEquals(QUANTITY_15, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void addFruit_zeroQuantity_doesNotChangeQuantity() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        storage.addFruit(FRUIT_APPLE, QUANTITY_0);
        assertEquals(QUANTITY_10, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void addFruit_negativeQuantity_doesNotChangeQuantity() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        storage.addFruit(FRUIT_APPLE, QUANTITY_NEGATIVE_5);
        assertEquals(QUANTITY_10, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void getFruitQuantity_existingFruit_returnsCorrectQuantity() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        assertEquals(QUANTITY_10, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void getFruitQuantity_nonExistingFruit_returnsZero() {
        assertEquals(QUANTITY_0, storage.getFruitQuantity(FRUIT_BANANA));
    }

    @Test
    void updateFruitQuantity_existingFruit_updatesQuantity() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        storage.updateFruitQuantity(FRUIT_APPLE, QUANTITY_5);
        assertEquals(QUANTITY_5, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void updateFruitQuantity_nonExistingFruit_setsQuantity() {
        storage.updateFruitQuantity(FRUIT_BANANA, QUANTITY_20);
        assertEquals(QUANTITY_20, storage.getFruitQuantity(FRUIT_BANANA));
    }

    @Test
    void updateFruitQuantity_zeroQuantity_setsQuantityToZero() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        storage.updateFruitQuantity(FRUIT_APPLE, QUANTITY_0);
        assertEquals(QUANTITY_0, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void updateFruitQuantity_negativeQuantity_updatesToNegativeValue() {
        storage.updateFruitQuantity(FRUIT_APPLE, QUANTITY_NEGATIVE_10);
        assertEquals(QUANTITY_NEGATIVE_10, storage.getFruitQuantity(FRUIT_APPLE));
    }

    @Test
    void getAllFruits_withFruits_returnsAllFruits() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        storage.addFruit(FRUIT_BANANA, QUANTITY_5);
        Map<String, Integer> fruits = storage.getAllFruits();

        assertEquals(EXPECTED_MAP_SIZE_2, fruits.size());
        assertEquals(QUANTITY_10, fruits.get(FRUIT_APPLE));
        assertEquals(QUANTITY_5, fruits.get(FRUIT_BANANA));
    }

    @Test
    void getAllFruits_modifyingReturnedMap_doesNotAffectStorage() {
        storage.addFruit(FRUIT_APPLE, QUANTITY_10);
        Map<String, Integer> fruits = storage.getAllFruits();
        fruits.put(FRUIT_BANANA, QUANTITY_20);

        assertNull(storage.getAllFruits().get(FRUIT_BANANA));
    }

}
