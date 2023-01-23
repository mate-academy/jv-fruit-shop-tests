package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

public class StorageTest {

    @BeforeClass
    public static void setUp() {
        Storage.FRUITS.put("banana", 20);
    }

    @Test
    public void storage_addValidDataToStorage_ok() {
        Integer expected = 20;
        Integer actual = Storage.FRUITS.get("banana");
        assertEquals(expected, actual);
    }

    @Test
    public void storage_addNullDataToStorage_notOk() {
        Integer expected = null;
        Integer actual = Storage.FRUITS.get(null);
        assertEquals(expected, actual);
    }

    @Test
    public void storage_addInvalidDataToStorage_notOk() {
        Integer expected = null;
        Integer actual = Storage.FRUITS.get("NotAdded");
        assertEquals(expected, actual);
    }
}
