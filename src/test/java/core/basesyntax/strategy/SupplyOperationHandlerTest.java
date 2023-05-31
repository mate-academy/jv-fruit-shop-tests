package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final String OPERATION_TYPE = "s";
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 20;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new SupplyOperationHandler();
    }

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit(FRUIT);
        transaction = new Transaction(OPERATION_TYPE,fruit,QUANTITY);
    }

    @Test
    public void supply_validValue_Ok() {
        Storage.storage.put(fruit, 50);
        operationHandler.apply(transaction);
        Integer expected = 70;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test
    public void supply_negativeValue_Ok() {
        Storage.storage.put(fruit,50);
        transaction.setQuantity(-20);
        operationHandler.apply(transaction);
        Integer expected = 30;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test
    public void supply_zeroValue_Ok() {
        Storage.storage.put(fruit,50);
        transaction.setQuantity(0);
        operationHandler.apply(transaction);
        Integer expected = 50;
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test (expected = RuntimeException.class)
    public void supply_storageEmpty_notOk() {
        operationHandler.apply(transaction);
    }

    @Test (expected = NullPointerException.class)
    public void supply_nullValue_notOk() {
        operationHandler.apply(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
