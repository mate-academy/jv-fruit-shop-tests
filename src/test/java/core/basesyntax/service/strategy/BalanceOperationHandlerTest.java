package core.basesyntax.service.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {

    @Test
    public void getCorrectBalance_Ok() {
        OperationHandler operationHandler = new BalanceOperationHandler();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(55);
        operationHandler.operate(fruitTransaction);
        int actual = Storage.fruits.get("banana");
        assertEquals(fruitTransaction.getQuantity(), actual);
    }

    @Test(expected = RuntimeException.class)
    public void nullArgument_NotOk() {
        new BalanceOperationHandler().operate(null);
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
