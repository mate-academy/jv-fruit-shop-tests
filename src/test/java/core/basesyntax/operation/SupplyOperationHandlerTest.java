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

public class SupplyOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeAll() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new SupplyOperationHandler(fruitTransactionDao);
    }

    @Test
    public void supplyHandler_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("kiwi", 5);
        operationHandler.applyNewAmount("kiwi", 25);
        operationHandler.applyNewAmount("kiwi", 13);
        operationHandler.applyNewAmount("kiwi", 6);
        Integer actual = Storage.fruitTransactionStorage.get("kiwi");
        Integer expected = 49;
        Assert.assertEquals("Balance from storage " + actual
                + " but should be " + expected + ".", expected, actual);
    }

    @Test
    public void supplyHandler_inValidValuesPass_NotOk() {
        Storage.fruitTransactionStorage.put("grape", 0);
        thrown.expect(RuntimeException.class);
        operationHandler.applyNewAmount("", 2);
    }

    @Test
    public void supplyHandler_nullValue_NotOk() {
        thrown.expect(RuntimeException.class);
        operationHandler.applyNewAmount(null, null);
    }

    @After
    public void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
