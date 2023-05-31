package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final Fruit TEST_FRUIT = new Fruit("banana");
    private static final int TEST_QUANTITY = 30;
    private static final String SUPPLY_OPERATION = "s";
    private OperationHandler supplyOperationHandler;

    @Before
    public void setUp() {
        Storage.storage.put(TEST_FRUIT, TEST_QUANTITY);
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void setSupplyOperationHandler_OK() {
        Transaction supplyTransaction = new Transaction(SUPPLY_OPERATION, TEST_FRUIT, 25);
        supplyOperationHandler.apply(supplyTransaction);
        int expected = 55;
        int actual = Storage.storage.get(TEST_FRUIT);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
