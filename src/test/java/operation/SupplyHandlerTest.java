package operation;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static SupplyHandler supplyHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        supplyHandler = new SupplyHandler();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void validSupplyOperation_OK() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        secondFruitTransaction.setFruit("banana");
        secondFruitTransaction.setQuantity(10);
        OperationHandler actual = supplyHandler.handle(fruitTransaction);
        OperationHandler expected = supplyHandler.handle(secondFruitTransaction);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
