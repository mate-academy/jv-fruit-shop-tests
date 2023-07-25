package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;
import java.util.Map;
import model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageDaoTests {
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();

        fruitStorageDao.set("banana", 100);
        fruitStorageDao.set("apple", 25);
    }

    @Test
    void getFruit_ok() {
        String fruitName = "banana";
        int fruitQuantity = 100;
        Fruit expected = new Fruit(fruitName, fruitQuantity);
        Fruit actual = fruitStorageDao.getFruit("banana");

        assertEquals(expected, actual,
                "Actual and expected fruit should be equals");
    }

    @Test
    void getInvalidFruit_notOk() {
        String fruitName = "coconut";
        assertThrows(RuntimeException.class, () -> fruitStorageDao.getFruit(fruitName),
                "There is no such fruit: " + fruitName);
    }

    @Test
    void set_ok() {
        String fruitName = "banana";
        int fruitQuantity = 50;
        Fruit expected = new Fruit(fruitName, fruitQuantity);
        fruitStorageDao.set("banana", 50);
        Fruit actual = fruitStorageDao.getFruit("banana");

        assertEquals(expected, actual, "Actual and expected fruit should be equal");
    }

    @Test
    void setNegativeQuantity_notOk() {
        fruitStorageDao.set("banana", -100);
        assertThrows(RuntimeException.class, () -> fruitStorageDao.getFruit("banana"),
                "Stored quantity can't be less than zero");
    }

    @Test
    void add_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 130);
        expected.put("apple", 35);

        fruitStorageDao.add("banana", 30);
        fruitStorageDao.add("apple", 10);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void addNegativeQuantity_notOk() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 100);
        expected.put("apple", 25);

        assertThrows(RuntimeException.class, () -> fruitStorageDao.add("banana", -3),
                "Fruit quantity can't be less than zero");

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void removeFruit_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 100);

        fruitStorageDao.remove("apple", 25);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void removeFruitQuantityPartly_ok() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 50);
        expected.put("apple", 25);

        fruitStorageDao.remove("banana", 50);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void removeFruitMoreThanStored_notOk() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 100);
        expected.put("apple", 25);

        assertThrows(RuntimeException.class,
                () -> fruitStorageDao.remove("banana", 150),
                "Stored quantity can't be less than zero");

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruits stocks must be equals");
    }

    @Test
    void removeNonExistenceFruit_notOk() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 100);
        expected.put("apple", 25);

        String fruitName = "coconut";

        assertThrows(RuntimeException.class,
                () -> fruitStorageDao.remove(fruitName, 50),
                "There is no such fruit type " + fruitName + " in storage");

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }

    @Test
    void getFruits() {
        Map<String,Integer> expected = new HashMap<>();
        expected.put("banana", 100);
        expected.put("apple", 25);

        Map<String,Integer> actual = fruitStorageDao.getFruits();

        assertEquals(expected, actual,
                "Actual and expected fruit stocks must be equals");
    }
}
