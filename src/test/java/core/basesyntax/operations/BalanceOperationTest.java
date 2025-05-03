package core.basesyntax.operations;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.dao.FruitImplemDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
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
    public void handleBalanceTransaction_ok() {
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
