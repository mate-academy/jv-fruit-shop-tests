package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandlerImpl;
import org.junit.*;

public class BalanceOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new BalanceOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("mango");
        transaction = new Transaction(Transaction.Operation.BALANCE, fruit, 52);
    }

    @Test
    public void operation_ApplyBalance_ok() {
        boolean emptyStorage =  Storage.storage.isEmpty();
        Assert.assertTrue(emptyStorage);
        operationHandler.apply(transaction);
        int actualSize = Storage.storage.size();
        Assert.assertEquals(1, actualSize);
    }

    @Test
    public void operation_ApplyNegativeBalance_notOk() {
        transaction.setQuantity(-1);
        operationHandler.apply(transaction);
        int expected = 0;
        int actualSize = Storage.storage.size();
        Assert.assertNotEquals(expected, actualSize);
    }


    @After
    public void clear() {
        Storage.storage.clear();
    }
}
