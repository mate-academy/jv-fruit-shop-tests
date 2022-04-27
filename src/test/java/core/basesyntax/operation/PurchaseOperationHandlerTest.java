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

public class PurchaseOperationHandlerTest {
    private static FruitTransactionDao fruitTransactionDao;
    private static OperationHandler operationHandler;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitTransactionDao);
    }

    @Test
    public void purchaseHandler_passValidValue_Ok() {
        Storage.fruitTransactionStorage.put("apple", 100);
        operationHandler.applyNewAmount("apple", 20);
        operationHandler.applyNewAmount("apple", 20);
        operationHandler.applyNewAmount("apple", 20);
        operationHandler.applyNewAmount("apple", 30);
        Integer actual = Storage.fruitTransactionStorage.get("apple");
        Integer expected = 10;
        Assert.assertEquals("Balance from storage " + actual
                + " but should be " + expected + ".", expected, actual);
    }

    @Test
    public void purchaseHandler_inValidValuesPass_NotOk() {
        Storage.fruitTransactionStorage.put("banana", 0);
        thrown.expect(RuntimeException.class);
        operationHandler.applyNewAmount("", 15);
    }

    @Test
    public void purchaseHandler_nullValue_NotOk() {
        thrown.expect(RuntimeException.class);
        operationHandler.applyNewAmount(null, null);
    }

    @After
    public void afterEach() {
        Storage.fruitTransactionStorage.clear();
    }
}
