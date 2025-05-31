package core.basesyntax.newtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitsDaoImplTest {
    private FruitsDaoImpl fruitsDao;

    @BeforeEach
    public void setUp() {
        fruitsDao = new FruitsDaoImpl();
        Storage.fruitStorage.clear();
    }

    @Test
    public void addFruit_addsFruitSuccessfully() {
        fruitsDao.addFruit("apple", 10);
        Map<String, Integer> fruits = fruitsDao.getAllFruits();
        assertEquals(1, fruits.size());
        assertEquals(10, fruits.get("apple"));
    }

    @Test
    public void getAllFruits_returnsEmptyMapInitially() {
        Map<String, Integer> fruits = fruitsDao.getAllFruits();
        assertTrue(fruits.isEmpty());
    }
}
