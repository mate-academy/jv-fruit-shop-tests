package core.basesyntax.dao.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageDaoImplTest {
    private FruitStorageDao fruitStorageDao;

    @BeforeEach
    void setUp() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @AfterEach
    void tearDown() {
        FruitStorage.fruitStorage.clear();
    }

    @Test
    void addFruit_ValidTransaction_Ok() {
        FruitTransaction balanceApple =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 200);
        FruitTransaction balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 300);
        fruitStorageDao.addFruit(balanceApple);
        fruitStorageDao.addFruit(balanceBanana);
        assertTrue(FruitStorage.fruitStorage.containsKey(balanceApple.getFruit()),
                "Apples should have been added to the Storage, but were not");
        assertTrue(FruitStorage.fruitStorage.containsKey(balanceBanana.getFruit()),
                "Bananas should have been added to the Storage, but were not");
        assertEquals(200, FruitStorage.fruitStorage.get("apple"),
                "Storage should contain 200 apples");
        assertEquals(300, FruitStorage.fruitStorage.get("banana"),
                "Storage should contain 300 bananas");
    }

    @Test
    void increaseQuantity_ValidTransactions_Ok() {
        FruitTransaction balanceApple =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 200);
        FruitTransaction balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 300);
        FruitTransaction supplyApple =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "apple", 100);
        FruitTransaction returnBanana =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        "banana", 200);
        fruitStorageDao.addFruit(balanceApple);
        fruitStorageDao.addFruit(balanceBanana);
        fruitStorageDao.increaseQuantity(supplyApple);
        fruitStorageDao.increaseQuantity(returnBanana);
        assertEquals(300, FruitStorage.fruitStorage.get("apple"),
                "Expected value should be 300");
        assertEquals(500, FruitStorage.fruitStorage.get("banana"),
                "Expected value should be 500");
    }

    @Test
    void decreaseQuantity_ValidTransaction_Ok() {
        FruitTransaction balanceApple =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple", 200);
        FruitTransaction balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana", 300);
        FruitTransaction purchaseApple =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "apple", 100);
        FruitTransaction purchaseBanana =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        "banana", 150);
        fruitStorageDao.addFruit(balanceApple);
        fruitStorageDao.addFruit(balanceBanana);
        fruitStorageDao.decreaseQuantity(purchaseApple);
        fruitStorageDao.decreaseQuantity(purchaseBanana);
        assertEquals(100, FruitStorage.fruitStorage.get("apple"),
                "Expected value should be 100");
        assertEquals(150, FruitStorage.fruitStorage.get("banana"),
                "Expected value should be 150");
    }

    @Test
    void getAllAsMap_ValidMap_Ok() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put("apple", 200);
        expectedMap.put("banana", 300);
        FruitStorage.fruitStorage.put("apple", 200);
        FruitStorage.fruitStorage.put("banana", 300);
        Map<String, Integer> actualMap = fruitStorageDao.getAllAsMap();
        assertEquals(expectedMap, actualMap, "Maps should be equal");
    }

    @Test
    void getQuantity_FruitIsInStorage_Ok() {
        FruitStorage.fruitStorage.put("apple", 200);
        int expectedQuantity = 200;
        int actualQuantity = fruitStorageDao.getQuantity("apple");
        assertEquals(expectedQuantity, actualQuantity,
                "Expected quantity is 200");
    }
}
