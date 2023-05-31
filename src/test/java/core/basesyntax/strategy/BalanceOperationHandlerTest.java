package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static final String OPERATION_TYPE = "b";
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 20;
    private Transaction transaction;
    private Fruit fruit;

    @BeforeClass
    public static void beforeClass() throws Exception {
        operationHandler = new BalanceOperationHandler();
    }

    @Before
    public void setUp() throws Exception {
        fruit = new Fruit(FRUIT);
        transaction = new Transaction(OPERATION_TYPE, fruit, QUANTITY);
    }

    @Test
    public void balance_validValue_Ok() {
        Integer expected = 20;
        operationHandler.apply(transaction);
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test
    public void balance_negativeValue_Ok() {
        Integer expected = -20;
        transaction.setQuantity(-20);
        operationHandler.apply(transaction);
        assertEquals(expected, Storage.storage.get(fruit));
    }

    @Test (expected = NullPointerException.class)
    public void balance_nullValue_notOk() {
        operationHandler.apply(null);
    }

    @After
    public void tearDown() throws Exception {
        Storage.storage.clear();
    }
}
