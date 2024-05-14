package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitsDaoImplTest {
    private FruitsDao fruitsDao;
    private Storage storage;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        fruitsDao = new FruitsDaoImpl(storage);
    }

    @AfterEach
    void tearDown() {
        storage.getFruitsInventory().clear();
    }

    @Test
    void balance_setFruitsQuantity_ok() {
        fruitsDao.balance("apple", 50);
        assertEquals(50, storage.getFruitsInventory().get("apple"));
    }

    @Test
    void supply_increaseFruitsQuantity_ok() {
        fruitsDao.balance("banana", 30);
        fruitsDao.supply("banana", 20);
        assertEquals(50, storage.getFruitsInventory().get("banana"));
    }

    @Test
    void purchase_decreaseFruitQuantity_ok() {
        fruitsDao.balance("orange", 20);
        fruitsDao.purchase("orange", 20);
        assertEquals(0, storage.getFruitsInventory().get("orange"));
    }

    @Test
    void returnFruits_increaseFruitQuantityAfterReturn_ok() {
        fruitsDao.balance("grape", 20);
        fruitsDao.returnFruits("grape", 15);
        assertEquals(35, storage.getFruitsInventory().get("grape"));
    }

    @Test
    void getInventory_ok() {
        fruitsDao.balance("apple", 50);
        fruitsDao.balance("banana", 30);
        Map<String, Integer> inventory = fruitsDao.getInventory();
        assertEquals(2, inventory.size());
        assertEquals(50, storage.getFruitsInventory().get("apple"));
        assertEquals(30, storage.getFruitsInventory().get("banana"));
    }

    @Test
    void supply_increaseNonExistingFruitQuantity_ok() {
        fruitsDao.supply("kiwi", 10);
        assertEquals(10,storage.getFruitsInventory().get("kiwi"));
    }

    @Test
    void purchase_decreaseNonExistingFruitQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> fruitsDao.purchase("kiwi", 15),
                "Fruit not found");
    }

    @Test
    void balance_nullFruits_notOk() {
        assertThrows(RuntimeException.class,
                () -> fruitsDao.balance(null, 10),
                "Fruit cannot be null");
    }

    @Test
    void balance_negativeQuantity_notOk() {
        assertThrows(RuntimeException.class,
                () -> fruitsDao.balance("apple", -5),
                "Quantity cannot be negative");
    }

    @Test
    void purchase_moreThanAvailable_notOk() {
        fruitsDao.balance("apple", 15);
        assertThrows(RuntimeException.class,
                () -> fruitsDao.purchase("apple", 25),
                "Not enough fruits in inventory");
    }
}
