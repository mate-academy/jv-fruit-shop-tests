package core.basesyntax.strategy.impl;

import static junit.framework.TestCase.fail;

import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private FruitTransaction fruitTransaction;
    private PurchaseOperationHandler purchaseOperationHandler;

    @Before
    public void setUp() {
        fruitTransaction = new FruitTransaction("p", "banana", 20);
        purchaseOperationHandler = new PurchaseOperationHandler(new FruitStorageDaoImpl());
    }

    @After
    public void tearDown() {
        Storage.fruitMap.clear();
    }

    @Test
    public void handle_removeFruit_OK() {
        Storage.fruitMap.put("banana", 20);
        Integer expected = 0;
        purchaseOperationHandler.handle(fruitTransaction);
        Assert.assertEquals(expected, Storage.fruitMap.get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void handle_null_notOK() {
        purchaseOperationHandler.handle(null);
    }

    @Test(expected = RuntimeException.class)
    public void handle_invalidOperation_notOK() {
        FruitTransaction transactionWithInvalidOperation
                = new FruitTransaction("t", "banana", 20);
        purchaseOperationHandler.handle(transactionWithInvalidOperation);
        fail("We need inform user about unknown operation and throw RuntimeException");
    }
}
