package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final Integer VALID_VALUE = 20;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationHandler();
    }

    @Before
    public void setUp() {
        FruitStorage.fruits.put("banana", 100);
    }

    @Test
    public void apply_addCorrectValueToStorage_ok() {
        Integer expected = 120;
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", VALID_VALUE));
        Integer actual = FruitStorage.fruits.get("banana");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                expected, "banana", actual), expected, actual);
    }

    @Test
    public void apply_addDataWithNullKey_ok() {
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, null, VALID_VALUE));
        Integer actual = FruitStorage.fruits.get(null);
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                VALID_VALUE, "banana", actual), VALID_VALUE, actual);
    }

    @Test
    public void apply_addDataWithEmptyKey_ok() {
        operationHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "", VALID_VALUE));
        Integer actual = FruitStorage.fruits.get("");
        assertEquals(String.format("Should return %d for key \"%s\" but was %d",
                VALID_VALUE, "banana", actual), VALID_VALUE, actual);
    }

    @After
    public void tearDown() {
        FruitStorage.fruits.clear();
    }
}
