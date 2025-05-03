package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.storage.Storage;
import java.util.Map;
import java.util.NoSuchElementException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private static final String FIRST_FRUIT = "banana";
    private static final String SECOND_FRUIT = "apple";
    private static final String THIRD_FRUIT = "orange";
    private static final String NON_EXISTENT_FRUIT = "strawberry";
    private StorageDaoImpl storageDao;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storageDao = new StorageDaoImpl();
        storage = new Storage();
        storageDao.save(FIRST_FRUIT, 10);
        storageDao.save(SECOND_FRUIT, 12);
        storageDao.save(THIRD_FRUIT, 8);
    }

    @AfterEach
    void tearDown() {
        storage.getFruitStorage().clear();
    }

    @Test
    void save_isOk() {
        int actualQuantity = storage.getFruitStorage().get(FIRST_FRUIT);
        assertEquals(10, actualQuantity,
                String.format("Incorrect result quantity for storage, %s", 10));
    }

    @Test
    void save_fruitIsNull_notOk() {
        assertThrows(RuntimeException.class, () -> storageDao.save(null, 10));
    }

    @Test
    void getAll_isOk() {
        Map<String, Integer> expected = storage.getFruitStorage();
        Map<String, Integer> actual = storageDao.getAll();
        assertEquals(expected, actual);
    }

    @Test
    void getQuantityByFruitName_isOk() {
        assertEquals(10, storageDao.getQuantityByFruitName(FIRST_FRUIT));
    }

    @Test
    void getQuantityByFruitName_nullValue_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> storageDao.getQuantityByFruitName(null));
    }

    @Test
    void getQuantityByFruitName_FruitNotInStorage_notOk() {
        assertThrows(NoSuchElementException.class,
                () -> storageDao.getQuantityByFruitName(NON_EXISTENT_FRUIT));
    }
}
