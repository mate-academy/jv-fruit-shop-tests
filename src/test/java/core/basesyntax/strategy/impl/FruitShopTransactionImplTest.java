package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.impl.Storage;
import core.basesyntax.strategy.FruitShopTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopTransactionImplTest {
    private static final String BALANCE = "b";
    private static final String SUPPLY = "s";
    private static final String PURCHASE = "p";
    private static final String RETURN = "r";
    private static final String DEFAULT_ACTIVITY_TYPE = BALANCE;
    private static final String DEFAULT_FRUIT = "apple";
    private static final Integer DEFAULT_QUANTITY = 10;
    private static FruitShopTransaction fruitShopTransaction;
    private Map<String, Integer> expected;

    @BeforeClass
    public static void setUp() {
        fruitShopTransaction = new FruitShopTransactionImpl();
    }

    @Before
    public void init() {
        Storage.fruits.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        expected = new HashMap<>();
    }

    @Test
    public void fruitTransaction_BalanceCase_Ok() {
        fruitShopTransaction.fruitTransaction(BALANCE, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, 20);
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_SupplyCase_Ok() {
        fruitShopTransaction.fruitTransaction(SUPPLY, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, 20);
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_ReturnCase_Ok() {
        fruitShopTransaction.fruitTransaction(RETURN, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, 20);
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_PurchaseCase_Ok() {
        fruitShopTransaction.fruitTransaction(PURCHASE, DEFAULT_FRUIT, DEFAULT_QUANTITY);
        Map<String, Integer> actual = Storage.fruits;
        expected.put(DEFAULT_FRUIT, 0);
        assertEquals(expected, actual);
    }

    @Test
    public void fruitTransaction_DefaultCase_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitShopTransaction
                .fruitTransaction("default", DEFAULT_FRUIT, DEFAULT_QUANTITY));
    }

    @Test
    public void fruitTransaction_NullActivityType_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitShopTransaction
                .fruitTransaction(null, DEFAULT_FRUIT, DEFAULT_QUANTITY));
    }

    @Test
    public void fruitTransaction_NullFruit_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitShopTransaction
                .fruitTransaction(DEFAULT_ACTIVITY_TYPE, null, DEFAULT_QUANTITY));
    }

    @Test
    public void fruitTransaction_NullQuantity_NotOk() {
        assertThrows(RuntimeException.class, () -> fruitShopTransaction
                .fruitTransaction(DEFAULT_ACTIVITY_TYPE, DEFAULT_FRUIT, null));
    }

    @After
    public void clear() {
        Storage.fruits.clear();
    }
}
