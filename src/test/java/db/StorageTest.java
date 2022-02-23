package db;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StorageTest {
    @Test
    public void storageCreating_Ok() {
        Storage storage = new Storage();
        assertEquals(storage.getFruitsInStorage().size(), 0);
    }
}
