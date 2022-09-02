package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final String OPERATION_TYPE = "p";
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 20;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new PurchaseOperationHandler();
    }

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit(FRUIT);
        transaction = new Transaction(OPERATION_TYPE,fruit,QUANTITY);
    }

    @Test
    public void purchase_validValue_Ok() {
        Integer expected = 30;
        Storage.storage.put(fruit,50);
        operationHandler.apply(transaction);
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test
    public void purchase_negativeValue_Ok() {
        Storage.storage.put(fruit,50);
        transaction.setQuantity(-20);
        operationHandler.apply(transaction);
        Integer expected = 70;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test (expected = RuntimeException.class)
    public void purchase_tooBigTransaction_notOk() {
        transaction.setQuantity(30);
        operationHandler.apply(transaction);
    }

    @Test (expected = RuntimeException.class)
    public void purchase_storageEmpty_notOk() {
        operationHandler.apply(transaction);
    }

    @Test (expected = NullPointerException.class)
    public void purchase_nullValue_notOk() {
        operationHandler.apply(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
