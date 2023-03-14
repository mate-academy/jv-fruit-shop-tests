package core.basesyntax.db;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Optional;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class StorageTest {
    public static final String testKey = "item";
    public static final int testValue = 1;

    @Before
    public void setUp() {
        Storage.storeItems.put(testKey, testValue);
    }

    @After
    public void cleanUpStorage() {
        Storage.storeItems.clear();
    }

    @Test
    public void storageIsEmptyOk() {
        cleanUpStorage();
        assertEquals(0, Storage.storeItems.size());
    }

    @Test
    public void containsItemOk() {
        assertEquals(testValue, Storage.storeItems.size());
    }

    @Test
    public void containsCorrectKeyOk() {
        assertTrue(Storage.storeItems.containsKey(testKey));
    }

    @Test
    public void correctValueOk() {
        assertEquals(Optional.of(testValue).get(), Storage.storeItems.get(testKey));
    }

    @Test
    public void containsNotExistingKeyNotOk() {
        assertNull(Storage.storeItems.get("123"));
    }
}
