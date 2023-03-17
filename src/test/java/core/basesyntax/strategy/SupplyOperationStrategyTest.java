package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.implementation.SupplyOperationStrategy;
import org.junit.After;
import org.junit.Test;

public class SupplyOperationStrategyTest {
    private static final String APPLE_KEY = "apple";
    private static final int APPLE_VALUE = 100;
    private static final int APPLE_TEST_VALUE = 120;
    private static final int APPLE_TEST_NEGATIVE_VALUE = -120;
    private static final int EXPECTED_VALUE = 220;

    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy = new SupplyOperationStrategy();

    @After
    public void clearMap() {
        FruitStorage.clear();
    }

    @Test
    public void applySupply_Ok() {
        FruitStorage.put(APPLE_KEY, APPLE_VALUE);
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        APPLE_KEY, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        assertEquals(EXPECTED_VALUE, (int) FruitStorage.get(APPLE_KEY));
    }

    @Test
    public void applySupply_newKey_Ok() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        APPLE_KEY, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        assertEquals(APPLE_TEST_VALUE, (int) FruitStorage.get(APPLE_KEY));
    }

    @Test(expected = RuntimeException.class)
    public void applySupply_nullFruit_notOk() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        null, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for null fruit type, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void applySupply_quantityLessThanZero_notOk() {
        fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.SUPPLY, APPLE_KEY, APPLE_TEST_NEGATIVE_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for quantity less than zero, but it wasn't");
    }
}

