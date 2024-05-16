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
    private static final int MOCK_APPLE_QUANTITY = 200;
    private static final int MOCK_BANANA_QUANTITY = 300;
    private static final String FRUIT_APPLE = "apple";
    private static final String FRUIT_BANANA = "banana";
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
                        FRUIT_APPLE, 200);
        FruitTransaction balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, 300);
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
                        FRUIT_APPLE, 200);
        FruitTransaction balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, 300);
        FruitTransaction supplyApple =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        FRUIT_APPLE, 100);
        FruitTransaction returnBanana =
                new FruitTransaction(FruitTransaction.Operation.RETURN,
                        FRUIT_BANANA, 200);
        fruitStorageDao.addFruit(balanceApple);
        fruitStorageDao.addFruit(balanceBanana);
        fruitStorageDao.increaseQuantity(supplyApple);
        fruitStorageDao.increaseQuantity(returnBanana);
        assertEquals(300, FruitStorage.fruitStorage.get(FRUIT_APPLE),
                "Expected value should be 300");
        assertEquals(500, FruitStorage.fruitStorage.get(FRUIT_BANANA),
                "Expected value should be 500");
    }

    @Test
    void decreaseQuantity_ValidTransaction_Ok() {
        FruitTransaction balanceApple =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_APPLE, 200);
        FruitTransaction balanceBanana =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        FRUIT_BANANA, 300);
        FruitTransaction purchaseApple =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_APPLE, 100);
        FruitTransaction purchaseBanana =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        FRUIT_BANANA, 150);
        fruitStorageDao.addFruit(balanceApple);
        fruitStorageDao.addFruit(balanceBanana);
        fruitStorageDao.decreaseQuantity(purchaseApple);
        fruitStorageDao.decreaseQuantity(purchaseBanana);
        assertEquals(100, FruitStorage.fruitStorage.get(FRUIT_APPLE),
                "Expected value should be 100");
        assertEquals(150, FruitStorage.fruitStorage.get(FRUIT_BANANA),
                "Expected value should be 150");
    }

    @Test
    void getAllAsMap_ValidMap_Ok() {
        Map<String, Integer> expectedMap = new HashMap<>();
        expectedMap.put(FRUIT_APPLE, MOCK_APPLE_QUANTITY);
        expectedMap.put(FRUIT_BANANA, MOCK_BANANA_QUANTITY);
        FruitStorage.fruitStorage.put(FRUIT_APPLE, MOCK_APPLE_QUANTITY);
        FruitStorage.fruitStorage.put(FRUIT_BANANA, MOCK_BANANA_QUANTITY);
        Map<String, Integer> actualMap = fruitStorageDao.getAllAsMap();
        assertEquals(expectedMap, actualMap, "Maps should be equal");
    }

    @Test
    void getQuantity_FruitIsInStorage_Ok() {
        FruitStorage.fruitStorage.put(FRUIT_APPLE, MOCK_APPLE_QUANTITY);
        int expectedQuantity = MOCK_APPLE_QUANTITY;
        int actualQuantity = fruitStorageDao.getQuantity(FRUIT_APPLE);
        assertEquals(expectedQuantity, actualQuantity,
                "Expected quantity is 200");
    }
}
