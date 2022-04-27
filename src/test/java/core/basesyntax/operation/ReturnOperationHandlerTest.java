package core.basesyntax.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new ReturnOperationHandler(fruitTransactionDao);
    }

    @Test
    public void applyNewAmount_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("orange", 15);
        operationHandler.applyNewAmount("orange", 3);
        operationHandler.applyNewAmount("orange", 8);
        Integer actual = Storage.fruitTransactionStorage.get("orange");
        Integer expected = 26;
        Assert.assertEquals("Balance from storage " + actual
                + " but should be " + expected + ".", expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void applyNewAmount_passInvalidValue_NotOk() {
        Storage.fruitTransactionStorage.put("grape", 0);
        operationHandler.applyNewAmount("", 2);
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
