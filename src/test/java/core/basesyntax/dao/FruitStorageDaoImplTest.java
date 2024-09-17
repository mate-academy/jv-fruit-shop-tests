package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Set;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageDaoImplTest {

    private static Storage storage;
    private static FruitStorageDaoImpl fruitStorageDao;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage(new HashMap<>());
        fruitStorageDao = new FruitStorageDaoImpl(storage);
    }

    @BeforeEach
    void setUp() {
        storage.getFruits().clear();
    }

    @Test
    void setBalance_validFruit_ok() {
        Fruit fruit = new Fruit("apple");
        boolean result = fruitStorageDao.setBalance(fruit, 50);
        assertTrue(result);
        assertEquals(50, storage.getFruits().get(fruit));
    }

    @Test
    void add_validFruit_ok() {
        Fruit fruit = new Fruit("banana");
        fruitStorageDao.setBalance(fruit, 20);
        boolean result = fruitStorageDao.add(fruit, 30);
        assertTrue(result);
        assertEquals(50, storage.getFruits().get(fruit));
    }

    @Test
    void subtract_validFruit_ok() {
        Fruit fruit = new Fruit("orange");
        fruitStorageDao.setBalance(fruit, 50);
        boolean result = fruitStorageDao.subtract(fruit, 20);
        assertTrue(result);
        assertEquals(30, storage.getFruits().get(fruit));
    }

    @Test
    void getBalance_validFruit_ok() {
        Fruit fruit = new Fruit("grape");
        fruitStorageDao.setBalance(fruit, 40);
        int balance = fruitStorageDao.getBalance(fruit);
        assertEquals(40, balance);
    }

    @Test
    void getAllFruits_ok() {
        Fruit fruit1 = new Fruit("peach");
        Fruit fruit2 = new Fruit("plum");
        fruitStorageDao.setBalance(fruit1, 10);
        fruitStorageDao.setBalance(fruit2, 15);
        Set<Fruit> fruits = fruitStorageDao.getAllFruits();
        assertTrue(fruits.contains(fruit1));
        assertTrue(fruits.contains(fruit2));
    }

    @Test
    void checkFruitBelongToStorage_fruitNotInStorage_notOk() {
        Fruit fruit = new Fruit("kiwi");
        Exception exception = assertThrows(RuntimeException.class, () -> {
            fruitStorageDao.getBalance(fruit);
        });
        assertTrue(exception.getMessage().contains("doesn't belong to the Storage"));
    }
}
