package core.basesyntax.service.operation;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceOperationHandler(new FruitDaoImpl());
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
