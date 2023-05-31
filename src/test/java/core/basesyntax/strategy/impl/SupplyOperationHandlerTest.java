package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyOperationHandler();
    }

    @Test
    public void calculate_Ok() {
        Integer expectResult = 20;
        operationHandler.calculate(new Transaction("apple", 20));
        Integer actual = Storage.fruitsStorage.get("apple");
        assertEquals(expectResult, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_NegativeAmount_NotOk() {
        operationHandler.calculate(new Transaction("apple", -20));
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
