package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class BalanceStrategyTest {
    private static final String DEFAULT_FRUIT_BANANA = "banana";
    private static final String DEFAULT_FRUIT_APPLE = "apple";
    private static final int DEFAULT_QUANTITY_10 = 10;
    private static final int DEFAULT_QUANTITY_35 = 35;
    private static final int DEFAULT_QUANTITY_NEGATIVE = -10;
    private static final int DEFAULT_QUANTITY_45 = 45;
    private final BalanceTransaction balanceTransaction = new BalanceTransaction();
    private final CalculationStrategy strategy = new CalculationStrategy();

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void calculateAndStore_balance_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_10);
        expected.put(DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_35);
        FruitTransaction transactionApple
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_10);
        FruitTransaction transactionBanana
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_35);
        balanceTransaction.calculateAndStore(transactionApple);
        balanceTransaction.calculateAndStore(transactionBanana);
        assertEquals("Test failed! Storage must contains "
                + expected + " but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test(expected = RuntimeException.class)
    public void calculateAndStore_balance_negativeQuantity_notOk() {
        FruitTransaction transactionApple
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_NEGATIVE);
        balanceTransaction.calculateAndStore(transactionApple);
    }

    @Test
    public void getCalculationServiceByLetter_balance_ok() {
        FruitTransaction balance
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_45);
        CalculationService service = strategy.getCalculationServiceByLetter(balance);
        assertEquals("Test failed! Method must return "
                + BalanceTransaction.class + ", but was "
                + service.getClass(), BalanceTransaction.class, service.getClass());
    }
}
