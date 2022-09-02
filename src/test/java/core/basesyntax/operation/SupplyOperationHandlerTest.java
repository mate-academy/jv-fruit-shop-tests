package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Before
    public void setUp() {
        Storage.storage.put("apple", 200);
    }

    @Test
    public void supplyOperationHandler_putValidData_ok() {
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 20);
        supplyOperationHandler.apply(supplyTransaction);
        Integer expected = 220;
        Integer actual = Storage.storage.get(supplyTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void supplyOperationHandler_zeroQuantity_ok() {
        FruitTransaction supplyTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 0);
        supplyOperationHandler.apply(supplyTransaction);
        Integer expected = 200;
        Integer actual = Storage.storage.get(supplyTransaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
