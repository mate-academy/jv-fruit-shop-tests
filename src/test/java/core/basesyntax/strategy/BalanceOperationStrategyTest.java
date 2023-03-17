package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.implementation.BalanceOperationStrategy;
import org.junit.After;
import org.junit.Test;

public class BalanceOperationStrategyTest {

    private static final String APPLE_KEY = "apple";
    private static final int APPLE_VALUE = 100;
    private static final int APPLE_TEST_VALUE = 120;
    private static final int APPLE_TEST_NEGATIVE_VALUE = -120;
    private static final int EXPECTED_VALUE = 220;

    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy = new BalanceOperationStrategy();

    @After
    public void clearMap() {
        FruitStorage.clear();
    }

    @Test
    public void applyBalance_Ok() {
        FruitStorage.put(APPLE_KEY, APPLE_VALUE);
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE_KEY, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        assertEquals(EXPECTED_VALUE, (int) FruitStorage.get(APPLE_KEY));
    }

    @Test
    public void applyBalance_newKey_Ok() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        APPLE_KEY, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        assertEquals(APPLE_TEST_VALUE, (int) FruitStorage.get(APPLE_KEY));
    }

    @Test(expected = RuntimeException.class)
    public void applyBalance_nullFruit_not_Ok() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        null, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for null fruit type, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void applyBalance_quantityLessThanZero_not_Ok() {
        fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, APPLE_KEY,
                        APPLE_TEST_NEGATIVE_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for quantity less than zero, but it wasn't");
    }
}
