package core.basesyntax.db.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.model.FruitTransaction;
import core.basesyntax.db.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    public static final String BANANA = "banana";
    public static final Integer DEFAULT_QUANTITY = 100;
    public static final FruitTransaction.Operation RETURN = FruitTransaction.Operation.RETURN;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        operationHandler = new ReturnOperationHandler();
        Storage.getAll().put(BANANA, DEFAULT_QUANTITY);
    }

    @Test
    public void apply_correctValue_ok() {
        Integer expected = 170;
        operationHandler.apply(new FruitTransaction(RETURN, BANANA, 70));
        Integer actual = Storage.getAll().get(BANANA);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void apply_notExistedProduct_notOk() {
        operationHandler.apply(new FruitTransaction(RETURN, null, DEFAULT_QUANTITY));
    }

    @AfterClass
    public static void tearDown() {
        Storage.getAll().clear();
    }
}
