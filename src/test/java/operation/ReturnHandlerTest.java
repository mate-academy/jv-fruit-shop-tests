package operation;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class ReturnHandlerTest {
    @Test
    public void returnOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        OperationHandler returnHandler = new ReturnHandler();
        returnHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get("banana");
        Integer expected = 60;
        Assert.assertEquals(expected,actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
