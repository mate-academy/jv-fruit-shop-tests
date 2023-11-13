package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FruitDaoTest {
    private StorageDao fruitDao;

    @BeforeEach
    void setUp() {
        FruitStorage.fruitStorage.put("banana", 20);
        fruitDao = new FruitDao();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void getStorage_expectedStorage_ok() {
        assertEquals(FruitStorage.fruitStorage, fruitDao.getStorage());
    }

    @Test
    void getQuantityByObjectType_expectedQuantity_ok() {
        int expectedQuantity = 20;
        String fruit = "banana";
        int actualQuantity = fruitDao.getQuantityByObjectType(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    void putToStorage_newFruit_ok() {
        String fruit = "orange";
        int quantity = 10;
        fruitDao.putToStorage(fruit, quantity);
        int actualQuantity = fruitDao.getQuantityByObjectType(fruit);
        assertEquals(quantity, actualQuantity);
    }
}
