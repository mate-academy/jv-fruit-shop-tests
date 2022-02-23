package core.basesyntax.service.impl;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.service.StorageCleaner;
import org.junit.BeforeClass;
import org.junit.Test;

public class StorageCleanerImplTest {
    private static StorageCleaner cleaner;

    @BeforeClass
    public static void beforeClass() {
        cleaner = new StorageCleanerImpl();
    }

    @Test
    public void clearStorage_Ok() {
        Storage.FRUIT_STORAGE.put(new Fruit("lemon"), 15);
        cleaner.clearStorage();
        assertTrue(Storage.FRUIT_STORAGE.isEmpty());
    }
}
