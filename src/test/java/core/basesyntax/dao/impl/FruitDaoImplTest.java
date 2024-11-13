package core.basesyntax.dao.impl;

import static core.basesyntax.db.Storage.storage;

import core.basesyntax.dao.FruitDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest {
    private final FruitDao fruitDao = new FruitDaoImpl();

    @BeforeEach
    void setUp() {
        storage.clear();
    }

    @Test
    void supply_fruitStorage_isOk() {
        String fruit = "banana";
        int quantity = 15;
        fruitDao.supplyFruit(fruit, quantity);

        Integer actual = storage.get(fruit);
        Integer expected = 15;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void supply_storageExistFruit_isOk() {
        String storageContainsApple = "apple";
        Integer storageContainsQuantity = 35;
        storage.put(storageContainsApple, storageContainsQuantity);
        String newApple = "apple";
        int newQuantity = 15;

        fruitDao.supplyFruit(newApple, newQuantity);
        Integer actualQuantity = storage.get("apple");
        Integer expected = 50;

        Assertions.assertEquals(actualQuantity, expected);
    }

    @Test
    void boughtFruit_isOk() {
        String fruit = "banana";
        int quantity = 15;
        storage.put(fruit, quantity);

        fruitDao.boughtFruit("banana", 13);
        int actual = storage.get("banana");
        int expected = 2;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void bought_quantityLessThanNull_throwRuntimeException() {
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fruitDao.boughtFruit("banana", -1));
        String actual = exception.getMessage();
        String expected = "Quantity is not valid";

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void bought_fruitDoesntExist_throwRuntimeException() {
        String fruit = "banana";
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fruitDao.boughtFruit("banana", 5));
        String actual = exception.getMessage();
        String expected = "The requested fruit is "
                + "currently unavailable." + fruit;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void bought_notEnoughQuantity_throwRuntimeException() {
        String fruit = "banana";
        int quantity = 5;
        storage.put(fruit, quantity);
        Exception exception = Assertions.assertThrows(RuntimeException.class, () ->
                fruitDao.boughtFruit("banana", 10));
        String actual = exception.getMessage();
        String expected = "We do not have sufficient "
                + "quantity of the requested fruit." + fruit;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void return_fruit_isOk() {
        String fruit = "banana";
        int quantity = 50;

        fruitDao.returnFruit(fruit, quantity);
        int actual = storage.get("banana");
        int expected = 50;

        Assertions.assertEquals(expected, actual);
    }

    @Test
    void return_fruitIsExist_isOk() {
        String fruit = "banana";
        int quantity = 50;
        storage.put(fruit, quantity);
        fruitDao.returnFruit(fruit, quantity);
        int actual = storage.get("banana");
        int expected = 100;
        Assertions.assertEquals(expected, actual);
    }
}
