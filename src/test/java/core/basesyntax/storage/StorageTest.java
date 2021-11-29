package core.basesyntax.storage;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StorageTest {
    @Test
    public void storageCreating_ok() {
        Storage storage = new Storage();
        assertEquals(storage.getFruitsInStorage().size(), 0);
    }
}
