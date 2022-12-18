package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static FruitTransaction fruitTransaction;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitTransaction = new FruitTransaction();
        operationHandler = new PurchaseOperationHandler();
    }

    @Test
    public void operate_correctPurchaseOperation_Ok() {
        Storage.fruits.put("banana", 100);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(50);
        operationHandler.operate(fruitTransaction);
        int bananaAmount = Storage.fruits.get("banana");
        assertEquals(50, bananaAmount);
    }

    @Test(expected = RuntimeException.class)
    public void operate_nullValue_NotOk() {
        new PurchaseOperationHandler().operate(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
