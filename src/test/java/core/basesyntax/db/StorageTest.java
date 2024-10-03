package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Map;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private static final Map<String, Integer> TEST_MAP = Map.of("banana", 10, "apple", 12);
    private static final Map<String, Integer> ALTER_TEST_MAP = Map.of("banana", 11, "apple", 11);

    @Test
    public void storage_getTest_ok() {
        Storage.setFruitStorage(TEST_MAP);
        assertEquals(Storage.getFruitStorage(), TEST_MAP);
    }

    @Test
    public void storage_setTest_ok() {
        Storage.setFruitStorage(ALTER_TEST_MAP);
        assertNotEquals(Storage.getFruitStorage(), TEST_MAP);
    }
}
