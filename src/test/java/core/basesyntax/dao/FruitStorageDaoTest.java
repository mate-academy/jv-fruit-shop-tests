package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitStorageDaoTest {
    private static final FruitStorageDao FRUIT_STORAGE_DAO = new FruitStorageDaoImpl();

    @Test
    void add_correctData_ok() {
        String fruit = "apple";
        int quantity = 10;
        FRUIT_STORAGE_DAO.add(fruit,quantity);
        Integer actual = FruitStorage.FRUIT_STORAGE.get(fruit);
        assertEquals(quantity,actual);
    }

    @Test
    void get_correctData_ok() {
        String fruit = "banana";
        int quantity = 11;
        FruitStorage.FRUIT_STORAGE.put(fruit, quantity);
        int actual = FRUIT_STORAGE_DAO.get(fruit);
        assertEquals(quantity,actual);
    }

    @Test
    void get_notExistingFruit_notOk() {
        assertThrows(RuntimeException.class, () -> FRUIT_STORAGE_DAO.get("banana"));
    }

    @AfterEach
    void tearDown() {
        FruitStorage.FRUIT_STORAGE.clear();
    }
}
