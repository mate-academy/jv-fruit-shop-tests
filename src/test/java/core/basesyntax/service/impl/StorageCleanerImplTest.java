package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.StorageCleaner;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class StorageCleanerImplTest {
    private StorageCleaner cleaner = new StorageCleanerImpl();

    @Test
    public void clearStorage_Ok() {
        Storage.FRUIT_STORAGE.put(new Fruit("lemon"), 15);
        cleaner.clearStorage();
        assertTrue(Storage.FRUIT_STORAGE.isEmpty());
    }
}
