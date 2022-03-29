package operation;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SupplyHandlerTest {
    @Test
    public void supplyOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        OperationHandler supplyHandler = new SupplyHandler();
        supplyHandler.handle(fruitTransaction);
        Integer actual = Storage.fruits.get("banana");
        Integer expected = 60;
        Assert.assertEquals(expected,actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
