package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final String OPERATION_TYPE = "r";
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 20;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new ReturnOperationHandler();
    }

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit(FRUIT);
        transaction = new Transaction(OPERATION_TYPE,fruit,QUANTITY);
    }

    @Test
    public void return_validValue_Ok() {
        Storage.storage.put(fruit,50);
        operationHandler.apply(transaction);
        Integer expected = 70;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test
    public void return_negativeValue_Ok() {
        Storage.storage.put(fruit,50);
        transaction.setQuantity(-20);
        operationHandler.apply(transaction);
        Integer expected = 30;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test
    public void return_zeroValue_Ok() {
        Storage.storage.put(fruit,50);
        transaction.setQuantity(0);
        operationHandler.apply(transaction);
        Integer expected = 50;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test (expected = RuntimeException.class)
    public void return_storageEmpty_notOk() {
        operationHandler.apply(transaction);
    }

    @Test (expected = NullPointerException.class)
    public void return_nullValue_notOk() {
        operationHandler.apply(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
