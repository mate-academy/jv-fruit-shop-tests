package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.DaoStorage;
import core.basesyntax.dao.DaoStorageImpl;
import core.basesyntax.db.StorageImpl;
import core.basesyntax.exception.DaoStorageException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DaoStorageTest {
    static final String FRUIT = "banana";
    static final int QUANTITY = 100;
    static final int NEGATIVE_QUANTITY = -10;
    private static DaoStorage daoStorage;

    @BeforeAll
    public static void setUp() {
        daoStorage = new DaoStorageImpl();
    }

    @BeforeEach
    public void beforeTest() {
        StorageImpl.getStorage().clear();
    }

    @Test
    public void daoStorage_putArgumentsNullOrNegative_notOk() {
        assertThrows(DaoStorageException.class,
                () -> daoStorage.setNewValue(null, QUANTITY));
        assertThrows(DaoStorageException.class,
                () -> daoStorage.setNewValue(FRUIT, null));
        assertThrows(DaoStorageException.class,
                () -> daoStorage.setNewValue(FRUIT, NEGATIVE_QUANTITY));
    }

    @Test
    public void daoStorage_mergeArgumentsNullOrNegative_notOk() {
        assertThrows(DaoStorageException.class,
                () -> daoStorage.concatenateValue(null, QUANTITY));
        assertThrows(DaoStorageException.class,
                () -> daoStorage.concatenateValue(FRUIT, null));
        assertThrows(DaoStorageException.class,
                () -> daoStorage.concatenateValue(FRUIT, NEGATIVE_QUANTITY));
    }

    @Test
    public void daoStorage_getArgumentsNull_notOk() {
        assertThrows(DaoStorageException.class, () -> daoStorage.getValue(null));
    }

    @Test
    public void daoStorage_merge_Ok() {
        int expected = QUANTITY + QUANTITY;

        StorageImpl.getStorage().put(FRUIT,QUANTITY);
        daoStorage.concatenateValue(FRUIT, QUANTITY);

        assertEquals(expected, StorageImpl.getStorage().get(FRUIT));
    }

    @Test
    public void daoStorage_put_Ok() {
        daoStorage.setNewValue(FRUIT, QUANTITY);

        assertTrue(StorageImpl.getStorage().containsKey(FRUIT));
        assertEquals(QUANTITY, StorageImpl.getStorage().get(FRUIT));
    }

    @Test
    public void daoStorage_get_Ok() {
        StorageImpl.getStorage().put(FRUIT, QUANTITY);

        assertEquals(QUANTITY, daoStorage.getValue(FRUIT));
    }

    @Test
    public void daoStorage_getMissingFruit_NotOk() {
        assertThrows(DaoStorageException.class, () -> daoStorage.getValue(FRUIT));
    }

    @Test
    public void daoStorage_getStatisticStorageEmpty_notOk() {
        assertThrows(DaoStorageException.class, () -> daoStorage.getStatistic());
    }

    @Test
    public void daoStorage_getStatistic_Ok() {
        StorageImpl.getStorage().put(FRUIT, QUANTITY);

        assertEquals(StorageImpl.getStorage().entrySet(), daoStorage.getStatistic());
    }
}
