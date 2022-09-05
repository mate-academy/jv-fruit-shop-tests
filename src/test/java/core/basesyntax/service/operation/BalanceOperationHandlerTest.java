package core.basesyntax.service.operation;

import static core.basesyntax.db.Storage.fruits;

import core.basesyntax.dao.FruitDaoImpl;
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
    public void handleBalanceTransaction_Ok() {
        balanceHandler.handle(fruitTransaction);
        int actual = fruits.get("guava");
        Assert.assertEquals(40, actual);
    }

    @After
    public void afterEachTest() {
        fruits.clear();
    }
}
