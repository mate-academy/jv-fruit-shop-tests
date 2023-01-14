package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final Integer VALID_VALUE = 20;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.put("banana", 100);
    }

    @Test
    public void apply_addCorrectValueToStorage_ok() {
        Integer expected = 80;
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", VALID_VALUE));
        Integer actual = FruitStorage.fruits.get("banana");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                expected, "banana", actual), expected, actual);
    }

    @Test
    public void apply_addDataWithNullKey_ok() {
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, null, VALID_VALUE));
        Integer actual = FruitStorage.fruits.get(null);
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                VALID_VALUE, "banana", actual), VALID_VALUE, actual);
    }

    @Test
    public void apply_addDataWithEmptyKey_ok() {
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "", VALID_VALUE));
        Integer actual = FruitStorage.fruits.get("");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                VALID_VALUE, "banana", actual), VALID_VALUE, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
