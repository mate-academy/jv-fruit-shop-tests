package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    private FruitsDaoImpl fruitsDao;

    @BeforeEach
    void setUp() {
        Storage.fruitStorage.clear(); // Очистити storage перед кожним тестом
        fruitsDao = new FruitsDaoImpl();
    }

    @Test
    void addFruit_shouldAddFruitToStorage() {
        fruitsDao.addFruit("apple", 10);
        assertEquals(10, Storage.fruitStorage.get("apple"));
    }

    @Test
    void addFruit_shouldOverwriteExistingFruitQuantity() {
        Storage.fruitStorage.put("banana", 5);
        fruitsDao.addFruit("banana", 20);
        assertEquals(20, Storage.fruitStorage.get("banana"));
    }

    @Test
    void getAllFruits_shouldReturnAllFruitsFromStorage() {
        Storage.fruitStorage.put("orange", 15);
        Storage.fruitStorage.put("kiwi", 7);

        Map<String, Integer> allFruits = fruitsDao.getAllFruits();

        assertEquals(2, allFruits.size());
        assertEquals(15, allFruits.get("orange"));
        assertEquals(7, allFruits.get("kiwi"));
    }
}
