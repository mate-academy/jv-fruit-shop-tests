package core.basesyntax.db;

import static core.basesyntax.storage.Storage.shop;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ShopDaoImpTest {
    private static ShopDao shopDao;

    @BeforeAll
    public static void setUp() {
        shopDao = new ShopDaoImpl();
    }

    @AfterEach
    void makeShopEmpty() {
        shop.clear();
    }

    @Test
    void addingFruitsToBalance_Ok() {
        shopDao.addFruitToBalance("apple", 0);
        shopDao.addFruitToBalance("pear", 1);
        shopDao.addFruitToBalance("pineapple", 50);
        shopDao.addFruitToBalance("banana", 1000);
        assertEquals(0, shop.get("apple"));
        assertEquals(1, shop.get("pear"));
        assertEquals(50, shop.get("pineapple"));
        assertEquals(1000, shop.get("banana"));
    }

    @Test
    void purchasingFruitsIncreasesBalance_Ok() {
        shopDao.addFruitToBalance("apple", 0);
        shopDao.addFruitToBalance("pear", 1);
        shopDao.addFruitToBalance("pineapple", 50);
        shopDao.addFruitToBalance("banana", 1000);
        shopDao.purchaseFruit("apple", 5);
        shopDao.purchaseFruit("pear", 6);
        shopDao.purchaseFruit("pineapple", 6);
        shopDao.purchaseFruit("banana", 7);
        assertEquals(5, shop.get("apple"));
        assertEquals(7, shop.get("pear"));
        assertEquals(56, shop.get("pineapple"));
        assertEquals(1007, shop.get("banana"));
    }

    @Test
    void addingFruitsToBalance_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> shopDao.addFruitToBalance("apple", -1));
        String expectedMessage = "The quantity is negative";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void buyingFruitWhichIsNotInBalance_NotOk() {
        Exception exception = assertThrows(RuntimeException.class,
                () -> shopDao.purchaseFruit("apple", 1));
        String expectedMessage = "There is no such fruit in shop";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    void purchasingNegativeQuantity_NotOk() {
        shopDao.addFruitToBalance("apple", 5);
        Exception exception = assertThrows(RuntimeException.class,
                () -> shopDao.purchaseFruit("apple", -10));
        String expectedMessage = "Balance is too low for this purchase";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

}
