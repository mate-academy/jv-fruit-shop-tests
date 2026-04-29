package core.basesyntax.db;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class StorageDaoImplTest {
    private StorageDaoImpl storageDao;

    @BeforeEach
    void beforeEach() {
        storageDao = new StorageDaoImpl();
        storageDao.getFruits().clear();
    }

    @Test
    void add_correctFruits_ok() {
        storageDao.add(new FruitTransaction(Operation.BALANCE, "apple", 20));
        storageDao.add(new FruitTransaction(Operation.PURCHASE, "banana", 12));
        storageDao.add(new FruitTransaction(Operation.RETURN, "cherry", 15));
        storageDao.add(new FruitTransaction(Operation.SUPPLY, "strawberry", 30));
        assertEquals(20, storageDao.getFruits().get("apple"));
        assertEquals(12, storageDao.getFruits().get("banana"));
        assertEquals(15, storageDao.getFruits().get("cherry"));
        assertEquals(30, storageDao.getFruits().get("strawberry"));
    }

    @Test
    void add_nullFruit_notOk() {
        assertThrows(NullPointerException.class, () -> storageDao.add(null));
    }

    @Test
    void actualQuantity_fruitNull_notOk() {
        assertThrows(RuntimeException.class, () -> storageDao.actualQuantity(null));
    }

    @Test
    void actualQuantity_notExistFruit_notOk() {
        assertThrows(RuntimeException.class, () -> storageDao.actualQuantity("apple"));
    }

    @Test
    void actualQuantity_existFruit_ok() {
        storageDao.getFruits().put("apple", 20);
        storageDao.getFruits().put("banana", 12);
        storageDao.getFruits().put("cherry", 15);
        storageDao.getFruits().put("strawberry", 30);
        assertEquals(20, storageDao.actualQuantity("apple"));
        assertEquals(12, storageDao.actualQuantity("banana"));
        assertEquals(15, storageDao.actualQuantity("cherry"));
        assertEquals(30, storageDao.actualQuantity("strawberry"));
    }

    @Test
    void updateQuantity_existingFruits_ok() {
        storageDao.getFruits().put("apple", 20);
        storageDao.getFruits().put("banana", 12);
        storageDao.getFruits().put("cherry", 15);
        storageDao.getFruits().put("strawberry", 30);
        storageDao.updateQuantity("apple", 1);
        storageDao.updateQuantity("banana", 2);
        storageDao.updateQuantity("cherry", 3);
        storageDao.updateQuantity("strawberry", 4);
        assertEquals(1, storageDao.getFruits().get("apple"));
        assertEquals(2, storageDao.getFruits().get("banana"));
        assertEquals(3, storageDao.getFruits().get("cherry"));
        assertEquals(4, storageDao.getFruits().get("strawberry"));
    }

    @Test
    void getData_storageContainsFruits_ok() {
        storageDao.getFruits().put("apple", 20);
        storageDao.getFruits().put("banana", 12);
        storageDao.getFruits().put("cherry", 15);
        storageDao.getFruits().put("strawberry", 30);
        Map<String, Integer> expected = new LinkedHashMap<>();
        expected.put("apple", 20);
        expected.put("banana", 12);
        expected.put("cherry", 15);
        expected.put("strawberry", 30);
        assertEquals(expected, storageDao.getData());
    }

    @Test
    void checkFruit_existingFruit_ok() {
        storageDao.getFruits().put("apple", 20);
        storageDao.getFruits().put("banana", 12);
        storageDao.getFruits().put("cherry", 15);
        storageDao.getFruits().put("strawberry", 30);
        assertTrue(storageDao.checkFruit("apple"));
        assertTrue(storageDao.checkFruit("banana"));
        assertTrue(storageDao.checkFruit("cherry"));
        assertTrue(storageDao.checkFruit("strawberry"));
    }

    @Test
    void checkFruit_missingFruit_notOk() {
        assertFalse(storageDao.checkFruit("apple"));
    }
}
