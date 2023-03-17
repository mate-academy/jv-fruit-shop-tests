package strategy.impl;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import strategy.OperationHandler;

public class SupplyOperationHandlerTest {
    private static final int BANANA_QUANTITY = 100;
    private static final int INVALID_BANANA_QUANTITY = -100;
    private static final String BANANA = "banana";
    private static final FruitTransaction expected =
            new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana", 100);
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    public void calculate_validData_Ok() {
        operationHandler.calculate(expected);
        int actualBanana = Storage.fruits.get(BANANA);
        Assert.assertEquals(
                "Some problem with SupplyOperationHandler",
                BANANA_QUANTITY, actualBanana);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_invalidData_notOk() {
        expected.setQuantity(INVALID_BANANA_QUANTITY);
        operationHandler.calculate(expected);
    }
}
