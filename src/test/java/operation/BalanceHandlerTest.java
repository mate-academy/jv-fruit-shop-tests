package operation;

import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceHandlerTest {
    private static BalanceHandler balanceHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceHandler = new BalanceHandler();
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void validBalanceOperation_OK() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(30);
        FruitTransaction secondFruitTransaction = new FruitTransaction();
        secondFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        secondFruitTransaction.setFruit("banana");
        secondFruitTransaction.setQuantity(30);
        OperationHandler actual = balanceHandler.handle(fruitTransaction);
        OperationHandler expected = balanceHandler.handle(secondFruitTransaction);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
