package core.basesyntax.operation;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ReturnOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new ReturnOperationHandler(fruitTransactionDao);
    }

    @Test
    public void returnHandler_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("orange", 15);
        operationHandler.applyNewAmount("orange", 3);
        operationHandler.applyNewAmount("orange", 8);
        Integer actual = Storage.fruitTransactionStorage.get("orange");
        Integer expected = 26;
        Assert.assertEquals("Balance from storage " + actual
                + " but should be " + expected + ".", expected, actual);
    }

    @Test
    public void returnHandler_inValidValuesPass_NotOk() {
        Storage.fruitTransactionStorage.put("grape", 0);
        thrown.expect(RuntimeException.class);
        operationHandler.applyNewAmount("", 2);
    }

    @Test
    public void returnHandler_nullValue_NotOk() {
        thrown.expect(RuntimeException.class);
        operationHandler.applyNewAmount(null, null);
    }

    @After
    public void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
