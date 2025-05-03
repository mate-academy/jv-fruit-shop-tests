package core.basesyntax.db;

import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageTest {
    public static final String APPLE = "apple";
    public static final String ORANGE = "orange";
    public static final String BANANA = "banana";
    public static final int APPLE_QUANTITY = 150;
    public static final int ORANGE_QUANTITY = 123;
    public static final int BANANA_QUANTITY = 436;
    private Storage storage;

    @BeforeEach
    public void init() {
        storage = new Storage();
        Map<String, Integer> data = storage.getData();
        data.put(APPLE, APPLE_QUANTITY);
        data.put(ORANGE, ORANGE_QUANTITY);
        data.put(BANANA, BANANA_QUANTITY);
    }

    @Test
    public void add_addFiveFruits_OK() {
        int expected = APPLE_QUANTITY + 5;
        storage.add(APPLE, 5);
        int actual = storage.getData().get(APPLE);
        Assertions.assertEquals(expected, actual);

        expected = ORANGE_QUANTITY + 5;
        storage.add(ORANGE, 5);
        actual = storage.getData().get(ORANGE);
        Assertions.assertEquals(expected, actual);

        expected = BANANA_QUANTITY + 5;
        storage.add(BANANA, 5);
        actual = storage.getData().get(BANANA);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void add_addNewFruit_OK() {
        String fruitName = "Dragon fruit";
        int fruitQuantity = 51;
        storage.add(fruitName, fruitQuantity);
        Assertions.assertTrue(storage.getData().containsKey(fruitName));
        int actual = storage.getData().get(fruitName);
        Assertions.assertEquals(fruitQuantity, actual);

        fruitName = "Pineapple";
        fruitQuantity = 2;
        storage.add(fruitName, fruitQuantity);
        Assertions.assertTrue(storage.getData().containsKey(fruitName));
        actual = storage.getData().get(fruitName);
        Assertions.assertEquals(fruitQuantity, actual);

    }

    @Test
    public void add_addNegativeQuantity_notOK() {
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.add(APPLE, -2));
    }

    @Test
    public void subtract_subtractThreeFruits_OK() {
        int expected = APPLE_QUANTITY - 3;
        storage.subtract(APPLE, 3);
        int actual = storage.getData().get(APPLE);
        Assertions.assertEquals(expected, actual);

        expected = ORANGE_QUANTITY - 3;
        storage.subtract(ORANGE, 3);
        actual = storage.getData().get(ORANGE);
        Assertions.assertEquals(expected, actual);

        expected = BANANA_QUANTITY - 3;
        storage.subtract(BANANA, 3);
        actual = storage.getData().get(BANANA);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void subtract_subtractNotAddedFruit_notOK() {
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract("Coconut", 5));
    }

    @Test
    public void subtract_subtractNegativeQuantity_notOK() {
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract(APPLE, -2));
    }

    @Test
    public void subtract_subtractMoreThanAvailable_notOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract(APPLE, APPLE_QUANTITY + 1));
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract(ORANGE, ORANGE_QUANTITY + 1));
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract(BANANA, BANANA_QUANTITY + 1));
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract(APPLE, APPLE_QUANTITY + 12));
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract(ORANGE, ORANGE_QUANTITY + 43));
        Assertions.assertThrows(RuntimeException.class,
                () -> storage.subtract(BANANA, BANANA_QUANTITY + 100));
    }
}
