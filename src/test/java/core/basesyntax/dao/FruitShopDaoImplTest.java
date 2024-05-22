package core.basesyntax.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitShopDaoImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private FruitShopDao fruitShopDao;
    private final Map<String, Integer> testFruitStatistic = Map.of(
            APPLE, 100,
            BANANA, 200,
            ORANGE, 300);

    @BeforeEach
    void setUp() {
        fruitShopDao = new FruitShopDaoImpl();
    }

    @Test
    void getBalanceByFruit_Ok() {
        int expectedBanana = 30;
        int expectedApple = 10;
        Storage.balanceStatistic.put(BANANA, expectedBanana);
        Storage.balanceStatistic.put(APPLE, expectedApple);
        int actualBanana = fruitShopDao.getBalanceByFruit(BANANA);
        int actualApple = fruitShopDao.getBalanceByFruit(APPLE);
        assertEquals(actualBanana, expectedBanana);
        assertEquals(actualApple, expectedApple);
    }

    @Test
    void getBalanceByFruit_invalidKey_notOk() {
        int expectedBanana = 30;
        Storage.balanceStatistic.put(BANANA, expectedBanana);
        assertThrows(NullPointerException.class,
                () -> fruitShopDao.getBalanceByFruit(APPLE));
    }

    @Test
    void getBalance_Ok() {
        Storage.balanceStatistic.putAll(testFruitStatistic);
        Map<String, Integer> actualTestBalance = fruitShopDao.getBalance();
        assertEquals(testFruitStatistic, actualTestBalance);
    }

    @Test
    void getBalance_emptyDao_notOk() {
        Map<String, Integer> emptyBalanceMap = new HashMap<>();
        Map<String, Integer> actualTestBalance = fruitShopDao.getBalance();
        assertEquals(emptyBalanceMap, actualTestBalance);
    }

    @Test
    void putBalanceStatistic_Ok() {
        int initialQuantityApple = 10;
        fruitShopDao.putBalanceStatistic(APPLE, initialQuantityApple);
        assertTrue(Storage.balanceStatistic.containsKey(APPLE));
        int actualQuantityApple = Storage.balanceStatistic.get(APPLE);
        assertEquals(initialQuantityApple, actualQuantityApple);
        int newQuantityApple = 30;
        fruitShopDao.putBalanceStatistic(APPLE, newQuantityApple);
        assertTrue(Storage.balanceStatistic.containsKey(APPLE));
        int newActualQuantityApple = Storage.balanceStatistic.get(APPLE);
        assertEquals(newQuantityApple, newActualQuantityApple);
    }

    @AfterEach
    void tearDown() {
        Storage.balanceStatistic.clear();
    }
}
