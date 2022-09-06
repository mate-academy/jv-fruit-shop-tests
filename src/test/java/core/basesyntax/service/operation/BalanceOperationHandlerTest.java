package core.basesyntax.service.operation;

import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceHandler;
    private FruitTransaction fruitTransaction;

    @BeforeClass
    public static void beforeClass() {
        balanceHandler = new BalanceOperationHandler(new FruitDaoImpl());
    }

    @Before
    public void setUp() {
        fruitTransaction = FruitTransaction.of(FruitTransaction.Operation.BALANCE, "guava", 40);
    }

    @Test
    public void handle_balanceTransaction_Ok() {
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
