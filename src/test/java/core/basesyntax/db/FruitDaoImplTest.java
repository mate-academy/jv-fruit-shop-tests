package core.basesyntax.db;

import core.basesyntax.db.storage.Storage;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    public static final Map<String,Integer> EXPECTED_RESULT = new TreeMap<>();

    @BeforeEach
    void setUp() {
        EXPECTED_RESULT.put("banana",20);
        EXPECTED_RESULT.put("orange",4);
        EXPECTED_RESULT.put("apple",15);
        EXPECTED_RESULT.put("grapple",76);
    }

    @AfterEach
    void reset() {
        EXPECTED_RESULT.clear();
        Storage.fruits.clear();
    }

    @Test
    void addingNewFruit_OK() {
        FruitDao actualResult = new FruitDaoImpl();
        actualResult.add("banana", 20);
        actualResult.add("orange", 4);
        actualResult.add("apple", 15);
        actualResult.add("grapple", 76);
        Assertions.assertEquals(EXPECTED_RESULT, Storage.fruits);
    }

    @Test
    void addingExistingFruit_OK() {
        FruitDao actualResult = new FruitDaoImpl();
        actualResult.add("banana", 10);
        actualResult.add("banana", 10);
        Assertions.assertEquals(EXPECTED_RESULT.get("banana"), Storage.fruits.get("banana"));
        actualResult.add("grapple", 16);
        actualResult.add("grapple", 20);
        actualResult.add("grapple", 40);
        Assertions.assertEquals(EXPECTED_RESULT.get("grapple"), Storage.fruits.get("grapple"));
    }

    @Test
    void subtractFruits_OK() {
        FruitDao actualResult = new FruitDaoImpl();
        actualResult.add("orange", 42);
        actualResult.subtract("orange", 38);
        Assertions.assertEquals(EXPECTED_RESULT.get("orange"), Storage.fruits.get("orange"));
        actualResult.add("apple", 54);
        actualResult.subtract("apple", 39);
        Assertions.assertEquals(EXPECTED_RESULT.get("apple"), Storage.fruits.get("apple"));
    }

    @Test
    void throwsExceptionsWithNotEnoughFruitsInStorage_OK() {
        FruitDao actualResult = new FruitDaoImpl();
        actualResult.add("orange", 42);
        try {
            actualResult.subtract("orange", 50);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("method should throws RuntimeExceptions:"
                + "\"There isn't enough \""
                + "fruit"
                + "\" in Storage, you can buy \""
                + "oldQuantity");
    }

    @Test
    void throwsExceptionsWithNotIdenticalFruitsInStorage_OK() {
        FruitDao actualResult = new FruitDaoImpl();
        actualResult.add("orange", 42);
        try {
            actualResult.subtract("pineapple", 50);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("method should throws RuntimeExceptions"
                + "\"There isn't \" + fruit + \" in Storage\"");
    }

    @Test
    void getFruitsFromStorage() {
        FruitDao fruitDao = new FruitDaoImpl();
        Storage.fruits.put("banana", 20);
        Storage.fruits.put("orange", 4);
        Storage.fruits.put("apple", 15);
        Storage.fruits.put("grapple", 76);
        Map<String, Integer> actualResult = fruitDao.getStorageQuantity();
        Assertions.assertEquals(EXPECTED_RESULT, actualResult);
    }
}
