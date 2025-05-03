package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new BalanceOperationHandler();
    }

    @Test
    public void operate_correctBalance_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(55);
        operationHandler.operate(fruitTransaction);
        int actual = Storage.fruits.get("banana");
        assertEquals(fruitTransaction.getQuantity(), actual);
    }

    @Test(expected = RuntimeException.class)
    public void operate_nullValue_NotOk() {
        new BalanceOperationHandler().operate(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
