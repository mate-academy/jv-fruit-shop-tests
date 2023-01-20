package core.basesyntax.db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StorageTest {

    @Test
    public void storage_AddValidDataToStorage_isOk() {
        Storage.FRUITS.put("banana", 20);
        Integer expected = 20;
        Integer actual = Storage.FRUITS.get("banana");
        assertEquals(actual, expected);
    }
}
