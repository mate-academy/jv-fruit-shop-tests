package core.basesyntax.dao.operations;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.dao.FruitImplemDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.OperationHandler;
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
