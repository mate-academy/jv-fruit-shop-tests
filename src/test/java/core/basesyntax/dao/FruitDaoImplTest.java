package core.basesyntax.dao;

import static core.basesyntax.Base.NUMBER_100;
import static core.basesyntax.Base.NUMBER_50;
import static core.basesyntax.model.BaseForModel.APPLE;
import static core.basesyntax.model.BaseForModel.BANANA;
import static core.basesyntax.model.BaseForModel.MANGO;
import static core.basesyntax.model.BaseForModel.TR_BALANCE_BANANA_100;
import static core.basesyntax.model.BaseForModel.TR_PURCHASE_APPLE_20;
import static core.basesyntax.model.BaseForModel.TR_RETURN_MANGO_10;
import static core.basesyntax.model.BaseForModel.TR_SUPPLY_APPLE_50;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.BaseTest;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class FruitDaoImplTest extends BaseTest {

    @Test
    void putTransaction_null_notOk() {
        assertThrows(NullPointerException.class, () -> DAO.putTransaction(null));
    }

    @Test
    void putTransaction_isOk() {
        DAO.putTransaction(TR_BALANCE_BANANA_100);
        final boolean actual = Storage.fruitTransactions.values().stream()
                .flatMap(Collection::stream)
                .anyMatch(transaction -> transaction.equals(TR_BALANCE_BANANA_100));
        assertTrue(actual, "Storage must contain transaction: " + TR_BALANCE_BANANA_100);
    }

    @Test
    void getTransactions_isOk() {
        Storage.fruitTransactions.put(BANANA, List.of(TR_BALANCE_BANANA_100));
        Storage.fruitTransactions.put(APPLE, List.of(TR_SUPPLY_APPLE_50, TR_PURCHASE_APPLE_20));
        Storage.fruitTransactions.put(MANGO, List.of(TR_RETURN_MANGO_10));
        final int expected = 4;
        final int actual = DAO.getTransactions().stream()
                .mapToInt(e -> e.getValue().size())
                .sum();
        assertEquals(expected, actual, "Transactions quantity expected: " + expected
                + ", actual is: " + actual);
    }

    @Test
    void getTransactions_byFruit_isOk() {
        Storage.fruitTransactions.put(BANANA, List.of(TR_BALANCE_BANANA_100));
        Storage.fruitTransactions.put(APPLE, List.of(TR_SUPPLY_APPLE_50, TR_PURCHASE_APPLE_20));
        assertTrue(DAO.getTransactions(BANANA).contains(TR_BALANCE_BANANA_100),
                "Transactions list must contain: " + TR_BALANCE_BANANA_100);
        assertTrue(DAO.getTransactions(APPLE).contains(TR_SUPPLY_APPLE_50),
                "Transactions list must contain: " + TR_SUPPLY_APPLE_50);
        assertTrue(DAO.getTransactions(APPLE).contains(TR_PURCHASE_APPLE_20),
                "Transactions list must contain: " + TR_PURCHASE_APPLE_20);
        assertTrue(DAO.getTransactions(MANGO).isEmpty(),
                "Transactions list for " + MANGO + " must be empty.");
    }

    @Test
    void saveBalance_null_notOk() {
        assertThrows(NullPointerException.class, () -> DAO.saveBalance(null));
    }

    @Test
    void saveBalance_isOk() {
        Map<Fruit, Integer> dataMap = new HashMap<>();
        dataMap.put(MANGO, NUMBER_100);
        dataMap.put(APPLE, NUMBER_50);
        DAO.saveBalance(dataMap);
        final Map<Fruit, Integer> fruitBalance = Storage.fruitBalance;
        assertEquals(NUMBER_100, (int) fruitBalance.get(MANGO), "Balance must contain 100 mango");
        assertEquals(NUMBER_50, (int) fruitBalance.get(APPLE), "Balance must contain 50 apples");
    }

    @Test
    void getBalance_isOk() {
        Storage.fruitBalance.put(MANGO, NUMBER_100);
        Storage.fruitBalance.put(APPLE, NUMBER_50);
        final Map<Fruit, Integer> balance = DAO.getBalance();
        final int actualMangoCount = balance.entrySet().stream()
                .filter(entry -> entry.getKey().equals(MANGO))
                .mapToInt(Map.Entry::getValue)
                .sum();
        assertEquals(NUMBER_100, actualMangoCount,
                "Balance must contain mango: " + actualMangoCount);
        final int actualAppleCount = balance.entrySet().stream()
                .filter(entry -> entry.getKey().equals(APPLE))
                .mapToInt(Map.Entry::getValue)
                .sum();
        assertEquals(NUMBER_50, actualAppleCount,
                "Balance must contain apple: " + actualMangoCount);
    }

    @Test
    void clear_isOk() {
        Storage.fruitBalance.put(MANGO, NUMBER_100);
        Storage.fruitTransactions.put(APPLE, List.of(TR_PURCHASE_APPLE_20, TR_SUPPLY_APPLE_50));
        DAO.clear();
        assertTrue(Storage.fruitTransactions.isEmpty(), "Transactions storage must be empty.");
        assertTrue(Storage.fruitBalance.isEmpty(), "Balance storage must be empty.");
    }
}
