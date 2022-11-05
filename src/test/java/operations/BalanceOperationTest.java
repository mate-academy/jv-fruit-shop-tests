package operations;

import static model.FruitTransaction.Operation.BALANCE;

import dao.FruitImplemDao;
import db.Storage;
import model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceOperation(new FruitImplemDao());
    }

    @Test
    public void handle_balanceTransaction_Ok() {
        FruitTransaction fruitTransaction = FruitTransaction.of(BALANCE, "guava", 40);
        int expected = 40;
        balanceHandler.handle(fruitTransaction);
        int actual = Storage.fruits.get("guava");
        Assert.assertEquals(expected, actual);
    }

    @After
    public void afterEachTest() {
        Storage.fruits.clear();
    }
}
