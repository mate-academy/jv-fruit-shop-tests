package core.basesyntax.service.operationtypes;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class PurchaseHandlerTest {
    private static int expectedAmount = 50;
    private static final String fruitName = "apple";
    private static final int amount = 100;
    private static final int correctPurchaseAmount = 50;
    private static final int incorrectPurchaseAmount = 110;
    private static OperationTypeHandler operationTypeHandler;

    @Before
    public void setUp() throws Exception {
        Storage.fruits.clear();
        operationTypeHandler = new PurchaseHandler();
        Storage.fruits.put(fruitName, amount);
    }

    @Test
    public void apply_correctPurchaseValue_Ok() {
        operationTypeHandler.apply(fruitName, correctPurchaseAmount);
        int expected = expectedAmount;
        int actual = Storage.fruits.get(fruitName);
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_withIncorrectValue_NotOk() {
        operationTypeHandler.apply(fruitName, incorrectPurchaseAmount);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}
