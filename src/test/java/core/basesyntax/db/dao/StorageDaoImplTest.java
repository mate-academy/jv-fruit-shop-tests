package core.basesyntax.db.dao;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final String APPLE_NAME = "apple";
    private static final String BANANA_NAME = "banana";
    private static final Integer APPLE_COUNT = 15;
    private static final Integer BANANA_COUNT = 20;
    private static StorageDao storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @Test
    void storageDaoAdd_twoFruits_Ok() {
        Map<String, Integer> expected = Map.of(APPLE_NAME, APPLE_COUNT,
                BANANA_NAME, BANANA_COUNT);

        storageDao.add(APPLE_NAME, APPLE_COUNT);
        storageDao.add(BANANA_NAME, BANANA_COUNT);

        Map<String, Integer> actual = Storage.fruitsCount;

        assertEquals(expected, actual);
    }

    @Test
    void storageDaoGet_twoFruits_Ok() {
        Map<String, Integer> expected = Map.of(APPLE_NAME, APPLE_COUNT,
                BANANA_NAME, BANANA_COUNT);

        storageDao.add(APPLE_NAME, APPLE_COUNT);
        storageDao.add(BANANA_NAME, BANANA_COUNT);

        Map<String, Integer> actual = storageDao.getInfo();

        assertEquals(expected, actual);
    }
}
