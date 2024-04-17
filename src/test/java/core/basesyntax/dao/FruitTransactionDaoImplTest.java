package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class FruitTransactionDaoImplTest {
    private final FruitTransactionDao fruitTransactionDao = new FruitTransactionDaoImpl();

    @AfterEach
    public void afterEachTest() {
        Storage.fruits.clear();
    }

    @Test
    public void update_severalField_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("something", 10);
        fruitTransactionDao.update("banana", 20);

        Map<String, Integer> nameQuantityMap = new HashMap<>();
        nameQuantityMap.put("banana", 20);
        nameQuantityMap.put("apple", 10);
        nameQuantityMap.put("something", 10);

        assertEquals(Storage.fruits, nameQuantityMap);
    }

    @Test
    public void update_notExistField_ok() {
        Storage.fruits.put("apple", 10);
        Storage.fruits.put("something", 10);
        fruitTransactionDao.update("banana", 20);

        Map<String, Integer> nameQuantityMap = new HashMap<>();
        nameQuantityMap.put("apple", 10);
        nameQuantityMap.put("something", 10);

        assertEquals(Storage.fruits, nameQuantityMap);
    }

    @Test
    public void update_nullField_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionDao.update(null, 10);
        });
    }

    @Test
    public void add_oneField_ok() {
        String name = "banana";
        int quantity = 10;
        fruitTransactionDao.add(name, quantity);

        assertEquals(Storage.fruits.get(name), quantity);
    }

    @Test
    public void add_severalFields_ok() {

        Map<String, Integer> nameQuantityMap = new HashMap<>();
        nameQuantityMap.put("banana", 10);
        nameQuantityMap.put("apple", 10);
        nameQuantityMap.put("something", 10);
        nameQuantityMap.put("tomato", 10);

        for (Map.Entry<String, Integer> entry : nameQuantityMap.entrySet()) {
            fruitTransactionDao.add(entry.getKey(), entry.getValue());
        }

        assertEquals(Storage.fruits, nameQuantityMap);
    }

    @Test
    public void add_severalSameFields_ok() {

        fruitTransactionDao.add("banana", 10);
        fruitTransactionDao.add("banana", 15);
        fruitTransactionDao.add("something", 10);
        fruitTransactionDao.add("something", 9);
        fruitTransactionDao.add("apple", 10);

        Map<String, Integer> nameQuantityMapExpect = new HashMap<>();
        nameQuantityMapExpect.put("banana", 15);
        nameQuantityMapExpect.put("apple", 10);
        nameQuantityMapExpect.put("something", 9);

        assertEquals(Storage.fruits, nameQuantityMapExpect);
    }

    @Test
    public void add_nullFields_throwsException() {
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionDao.add(null, 10);
        });
    }

    @Test
    public void getByName_oneField_ok() {
        Storage.fruits.put("banana", 10);

        assertEquals(10, fruitTransactionDao.getByName("banana"));
    }

    @Test
    public void getByName_severalFields_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("appdle", 10);
        Storage.fruits.put("Tdsa", 10);
        Storage.fruits.put("apple", 9);
        Storage.fruits.put("dfsfd", 16);

        for (Map.Entry<String, Integer> entry : Storage.fruits.entrySet()) {
            assertEquals(fruitTransactionDao.getByName(entry.getKey()), entry.getValue());
        }
    }

    @Test
    public void getByName_nullFields_throwsException() {
        Storage.fruits.put(null, 10);
        assertThrows(RuntimeException.class, () -> {
            fruitTransactionDao.getByName(null);
        });
    }

    @Test
    public void getByName_notExist_throwsException() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("appdle", 10);

        assertThrows(RuntimeException.class, () -> {
            fruitTransactionDao.getByName("notExist");
        });
    }

    @Test
    public void getFull_severalFields_ok() {
        Storage.fruits.put("banana", 10);
        Storage.fruits.put("appdle", 10);
        Storage.fruits.put("Tdsa", 10);
        Storage.fruits.put("apple", 9);
        Storage.fruits.put("dfsfd", 16);

        Map<String, Integer> nameQuantityMapExpect = new HashMap<>();
        nameQuantityMapExpect.put("banana", 10);
        nameQuantityMapExpect.put("appdle", 10);
        nameQuantityMapExpect.put("Tdsa", 10);
        nameQuantityMapExpect.put("apple", 9);
        nameQuantityMapExpect.put("dfsfd", 16);

        assertEquals(nameQuantityMapExpect.entrySet(), fruitTransactionDao.getFull());
    }

    @Test
    public void getFull_oneFields_ok() {
        Storage.fruits.put("banana", 10);

        Map<String, Integer> nameQuantityMapExpect = new HashMap<>();
        nameQuantityMapExpect.put("banana", 10);

        assertEquals(nameQuantityMapExpect.entrySet(), fruitTransactionDao.getFull());
    }

    @Test
    public void getFull_empty_ok() {
        Map<String, Integer> nameQuantityMapExpect = new HashMap<>();
        assertEquals(nameQuantityMapExpect.entrySet(), fruitTransactionDao.getFull());
    }
}
