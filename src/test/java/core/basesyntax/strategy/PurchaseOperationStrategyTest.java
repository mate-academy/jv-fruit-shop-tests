package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.implementation.PurchaseOperationStrategy;
import org.junit.After;
import org.junit.Test;

public class PurchaseOperationStrategyTest {

    private static final String APPLE_KEY = "apple";
    private static final int APPLE_VALUE = 100;
    private static final int APPLE_TEST_VALUE = 60;
    private static final int APPLE_TEST_NEGATIVE_VALUE = -120;
    private static final int EXPECTED_VALUE = 40;
    private static final int LARGER_THAN_AMOUNT_VALUE = 160;

    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy = new PurchaseOperationStrategy();

    @After
    public void clearMap() {
        FruitStorage.clear();
    }

    @Test
    public void applyPurchase_Ok() {
        FruitStorage.put(APPLE_KEY, APPLE_VALUE);
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE_KEY, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        assertEquals(EXPECTED_VALUE, (int) FruitStorage.get(APPLE_KEY));
    }

    @Test(expected = RuntimeException.class)
    public void applyPurchase_newKey_Ok() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        APPLE_KEY, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for null amount, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void applyPurchase_nullFruit_not_Ok() {
        fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                        null, APPLE_TEST_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for null fruit type, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void applyPurchase_quantityLessThanZero_not_Ok() {
        fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, APPLE_KEY,
                        APPLE_TEST_NEGATIVE_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for quantity less than 0, but it wasn't");
    }

    @Test(expected = RuntimeException.class)
    public void applyPurchase_quantityMoreThanAmount_not_Ok() {
        FruitStorage.put(APPLE_KEY, APPLE_VALUE);
        fruitTransaction =
                new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, APPLE_KEY,
                        LARGER_THAN_AMOUNT_VALUE);
        operationStrategy.apply(fruitTransaction);
        fail("Expected " + RuntimeException.class.getName()
                + " to be thrown for quantity more than amount, but it wasn't");
    }
}
