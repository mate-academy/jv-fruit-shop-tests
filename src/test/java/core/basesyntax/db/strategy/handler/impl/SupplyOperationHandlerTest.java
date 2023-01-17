package core.basesyntax.db.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.db.model.FruitTransaction;
import core.basesyntax.db.strategy.OperationHandler;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    public static final String BANANA = "banana";
    public static final Integer DEFAULT_QUANTITY = 55;
    public static final FruitTransaction.Operation SUPPLY = FruitTransaction.Operation.SUPPLY;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void setUp() {
        operationHandler = new SupplyOperationHandler();
        Storage.getAll().put(BANANA, DEFAULT_QUANTITY);
    }

    @Test
    public void apply_correctValue_ok() {
        Integer expected = 145;
        operationHandler.apply(new FruitTransaction(SUPPLY, BANANA, 90));
        Integer actual = Storage.getAll().get(BANANA);
        assertEquals(expected, actual);
    }

    @Test (expected = NullPointerException.class)
    public void apply_notExistedProduct_notOk() {
        operationHandler.apply(new FruitTransaction(SUPPLY, null, DEFAULT_QUANTITY));
    }

    @AfterClass
    public static void tearDown() {
        Storage.getAll().clear();
    }
}
