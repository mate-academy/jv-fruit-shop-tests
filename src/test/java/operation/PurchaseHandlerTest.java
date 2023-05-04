package operation;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseHandlerTest {
    @Test
    public void purchaseOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        Storage.fruits.put("banana",50);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(20);
        OperationHandler purchaseHandler = new PurchaseHandler();
        purchaseHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get("banana");
        Integer expected = 30;
        Assert.assertEquals(expected,actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
