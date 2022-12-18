package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {

    @Test
    public void getCorrectReturnOperation_Ok() {
        Storage.fruits.put("banana", 100);

        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(50);

        OperationHandler operationHandler = new ReturnOperationHandler();
        operationHandler.operate(fruitTransaction);

        int bananaAmount = Storage.fruits.get("banana");
        assertEquals(150, bananaAmount);
    }

    @Test(expected = RuntimeException.class)
    public void nullArgument_NotOk() {
        new ReturnOperationHandler().operate(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
