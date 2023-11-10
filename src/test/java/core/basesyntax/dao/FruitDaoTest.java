package core.basesyntax.dao;

import core.basesyntax.db.FruitStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class FruitDaoTest {
    private StorageDao fruitDao;

    @BeforeEach
    void initialize() {
        FruitStorage.fruitStorage.put("banana", 20);
        fruitDao = new FruitDao();
    }

    @Test
    void getStorage_expectedStorage_ok() {
        assertSame(FruitStorage.fruitStorage, fruitDao.getStorage());
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
