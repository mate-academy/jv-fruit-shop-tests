package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    public static final String APPLE = "apple";
    public static final int INITIAL_QUANTITY = 10;
    public static final int REMOVED_QUANTITY = 5;
    public static final int REMOVED_BEYOND_ZERO_QUANTITY = 10;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        storage = new Storage();
    }

    @Test
    public void testAddFruit() {
        storage.addFruit(APPLE, INITIAL_QUANTITY);
        assertEquals(Integer.valueOf(INITIAL_QUANTITY), storage.getFruitQuantities().get(APPLE));
    }

    @Test
    public void testRemoveFruit() {
        storage.addFruit(APPLE, INITIAL_QUANTITY);
        storage.removeFruit(APPLE, REMOVED_QUANTITY);
        assertEquals(Integer.valueOf(INITIAL_QUANTITY - REMOVED_QUANTITY),
                storage.getFruitQuantities().get(APPLE));
    }

    @Test
    public void testRemoveFruitBeyondZero() {
        storage.addFruit(APPLE, REMOVED_QUANTITY);
        storage.removeFruit(APPLE, REMOVED_BEYOND_ZERO_QUANTITY);
        assertEquals(Integer.valueOf(REMOVED_QUANTITY - REMOVED_BEYOND_ZERO_QUANTITY),
                storage.getFruitQuantities().get(APPLE));
    }
}
