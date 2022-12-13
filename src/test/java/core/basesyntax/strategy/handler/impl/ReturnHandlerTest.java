package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnHandlerTest {
    private static final FruitTransaction returnFruitTransaction
            = new FruitTransaction(Operation.RETURN, "apple", 100);
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnHandler();
    }

    @Test
    public void returnOperationResult_Ok() {
        FruitTransaction actualFruitTransaction
                = operationHandler.getOperationResult(returnFruitTransaction);
        assertEquals(returnFruitTransaction, actualFruitTransaction);
    }

}
