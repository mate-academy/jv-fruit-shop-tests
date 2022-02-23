package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class StorageTest {
    private static final int EXPECTED_SIZE_FOR_EXISTS = 1;
    private static final int AMOUNT = 30;
    private static final String FRUIT = "apple";
    private static Storage storage;
    private final Map<String, Integer> expectedStorage;

    public StorageTest() {
        storage = new Storage();
        expectedStorage = new HashMap<>();
    }

    @Test
    public void getFruitsStorage_Ok() {
        expectedStorage.put(FRUIT,AMOUNT);
        storage.setFruitsStorage(expectedStorage);
        assertEquals(expectedStorage.entrySet(), storage.getFruitsStorage().entrySet());
    }

    @Test
    public void setFruitsStorage_Ok() {
        expectedStorage.put(FRUIT, AMOUNT);
        storage.setFruitsStorage(expectedStorage);
        assertEquals(EXPECTED_SIZE_FOR_EXISTS, storage.getFruitsStorage().size());
    }
}
