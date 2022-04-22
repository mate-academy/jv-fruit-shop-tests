package operation;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class BalanceHandlerTest {

    @Test
    public void balanceOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        OperationHandler balanceHandler = new BalanceHandler();
        balanceHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get("banana");
        Integer expected = 60;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.fruits.clear();
    }
}
