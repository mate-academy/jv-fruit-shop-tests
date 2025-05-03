package core.basesyntax.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new BalanceOperationHandler(fruitTransactionDao);
    }

    @Test
    public void applyNewAmount_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("banana", 0);
        Integer actual = operationHandler.applyNewAmount("banana", 20);
        Integer expected = 20;
        Assert.assertEquals("Balance from storage " + actual
                + " but should be " + expected + ".", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void applyNewAmount_passInvalidValue_NotOk() {
        Storage.fruitTransactionStorage.put("banana", 0);
        operationHandler.applyNewAmount("", 20);
    }

    @Test(expected = RuntimeException.class)
    public void applyNewAmount_nullValue_NotOk() {
        operationHandler.applyNewAmount(null, null);
    }

    @After
    public void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
