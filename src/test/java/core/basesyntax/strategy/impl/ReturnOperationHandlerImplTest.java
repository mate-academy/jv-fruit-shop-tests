package core.basesyntax.strategy.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.OperationHandler;
import org.junit.*;

public class ReturnOperationHandlerImplTest {
    private static OperationHandler operationHandler;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeAll() {
        operationHandler = new ReturnOperationHandlerImpl();
    }

    @Before
    public void setUp() {
        fruit = new Fruit("mango");
        Storage.storage.put(fruit, 45);
        transaction = new Transaction(Transaction.Operation.RETURN, fruit, 10);
    }

    @Test
    public void operation_applyReturn_ok() {
        operationHandler.apply(transaction);
        int actual = Storage.storage.get(fruit);
        int expected = 55;
        Assert.assertEquals(expected, actual);
    }

    @After
    public void clear() {
        Storage.storage.clear();
    }

}
