package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.put("apple", 200);
    }

    @Test
    public void returnOperationHandler_putValidData_ok() {
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        returnOperationHandler.apply(returnTransaction);
        Integer expected = 220;
        Integer actual = Storage.storage.get(returnTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnOperationHandler_zeroQuantity_ok() {
        FruitTransaction returnTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 0);
        returnOperationHandler.apply(returnTransaction);
        Integer expected = 200;
        Integer actual = Storage.storage.get(returnTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
