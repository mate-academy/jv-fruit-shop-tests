package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitsDaoImplTest {
    private FruitsDao fruitsDao;

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear();
        fruitsDao = new FruitsDaoImpl();
    }

    @Test
    void addFruit_shouldAddEntryToStorage() {
        fruitsDao.addFruit("apple", 10);
        assertEquals(10, Storage.fruitStorage.get("apple"));
    }

    @Test
    void getAllFruits_shouldReturnAllEntries() {
        Storage.fruitStorage.put("banana", 20);
        Map<String, Integer> fruits = fruitsDao.getAllFruits();
        assertEquals(1, fruits.size());
        assertEquals(20, fruits.get("banana"));
    }

    @Test
    void addFruit_shouldAddFruitToStorage() {
        fruitsDao.addFruit("apple", 10);
        assertEquals(10, Storage.fruitStorage.get("apple"));
    }

    @Test
    void getAllFruits_shouldReturnStorageMap() {
        Storage.fruitStorage.put("banana", 5);
        Map<String, Integer> allFruits = fruitsDao.getAllFruits();
        assertTrue(allFruits.containsKey("banana"));
        assertEquals(5, allFruits.get("banana"));
    }
}
