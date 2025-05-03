package core.basesyntax.strategy.handlers;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private Fruit defaultFruit;
    private FruitTransaction defaultTransaction;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() {
        defaultFruit = new Fruit("banana");
        defaultTransaction = new FruitTransaction();
        defaultTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        defaultTransaction.setFruit(defaultFruit);
        defaultTransaction.setQuantity(0);
    }

    @Test
    public void purchase_validData_Ok() {
        Storage.storage.put(defaultFruit, 10);
        defaultTransaction.setQuantity(5);
        operationHandler.apply(defaultTransaction);
        Assert.assertEquals("Expected another value: 5",
                Integer.valueOf(5),
                Storage.storage.get(defaultFruit));
    }

    @Test (expected = RuntimeException.class)
    public void purchase_ProductOutOfStock_NotOk() {
        Storage.storage.put(defaultFruit, 10);
        defaultTransaction.setQuantity(15);
        operationHandler.apply(defaultTransaction);
    }

    @Test
    public void purchase_QuantityEqualToStockBalance_Ok() {
        Storage.storage.put(defaultFruit, 5);
        defaultTransaction.setQuantity(5);
        operationHandler.apply(defaultTransaction);
        Assert.assertEquals("Expected another value: 0",
                Integer.valueOf(0),
                Storage.storage.get(defaultFruit));
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
