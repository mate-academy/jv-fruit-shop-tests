package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.Map;
import org.junit.jupiter.api.Test;

class StorageTest {
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";
    private static final String CHERRY = "chery";
    private static final int ZERO_ELEMENTS = 0;
    private static final int TEST_QUANTITY = 1;

    public void setUp() {
        Storage.updateDb(APPLE, ZERO_ELEMENTS);
        Storage.updateDb(BANANA, ZERO_ELEMENTS);
    }

    @Test
    public void updateDb_getQuantity_correct_ok() {
        Storage.updateDb(APPLE, TEST_QUANTITY);
        Storage.updateDb(BANANA, TEST_QUANTITY);
        assertEquals(TEST_QUANTITY, Storage.getQuantity(APPLE));
        assertEquals(TEST_QUANTITY, Storage.getQuantity(BANANA));
    }

    @Test
    public void updateDb_isCopyOfMapReturned_ok() {
        Storage.updateDb(APPLE, TEST_QUANTITY);
        Map<String, Integer> testMap = Storage.readDb();
        testMap.put(CHERRY, TEST_QUANTITY);
        assertNull(Storage.getQuantity(CHERRY));
    }

    @Test
    void readDb_correctReadDb_ok() {
        Storage.updateDb(APPLE, TEST_QUANTITY);
        Storage.updateDb(BANANA, TEST_QUANTITY);
        Map<String, Integer> testMap = Storage.readDb();
        assertEquals(TEST_QUANTITY, testMap.get(APPLE));
        assertEquals(TEST_QUANTITY, testMap.get(BANANA));
    }
}
