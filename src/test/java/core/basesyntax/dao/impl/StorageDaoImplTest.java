package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.models.InvalidDataException;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {

    private static final String FRUIT = "apple";
    private static final int QUANTITY = 10;
    private static StorageDaoImpl storageDao;

    @BeforeAll
    static void beforeAll() {
        storageDao = new StorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getStoredFruits().clear();
    }

    @Test
    void getStoredQuantity_Ok() {
        Storage.getStoredFruits().put(FRUIT, QUANTITY);
        int actual = storageDao.getStoredQuantity(FRUIT);
        assertEquals(QUANTITY, actual);
    }

    @Test
    void getStoredQuantity_ifFruitIsAbsent_Ok() {
        int expected = 0;
        int actual = storageDao.getStoredQuantity(FRUIT);
        assertEquals(expected, actual);
    }

    @Test
    void getStoredQuantity_nullValueArgument_NotOk() {
        String nullString = null;
        assertThrows(InvalidDataException.class, () -> storageDao.getStoredQuantity(nullString));
    }

    @Test
    void getStoredQuantity_emptyArgument_NotOk() {
        String emptyString = "";
        assertThrows(InvalidDataException.class, () -> storageDao.getStoredQuantity(emptyString));
    }

    @Test
    void addFruit_addingValidData_Ok() {
        storageDao.addFruit(FRUIT, QUANTITY);
        Set<Map.Entry<String, Integer>> entries = Storage.getStoredFruits().entrySet();
        String expected = FRUIT + QUANTITY;
        String actual = entries.stream()
                .map(s -> s.getKey() + s.getValue())
                .findAny()
                .orElseThrow(() -> new RuntimeException("Storage is empty"));
        assertEquals(expected, actual);
    }

    @Test
    void addFruit_nullValueFruit_NotOk() {
        String fruit = null;
        assertThrows(InvalidDataException.class, () -> storageDao.addFruit(fruit, QUANTITY));
    }
}
