package core.basesyntax.strategy.handler.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyHandlerTest {
    private static final FruitTransaction supplyFruitTransaction
            = new FruitTransaction(Operation.SUPPLY, "apple", 100);
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new SupplyHandler();
    }

    @Test
    public void getOperationResult_supplyOperation_ok() {
        FruitTransaction actualFruitTransaction
                = operationHandler.getOperationResult(supplyFruitTransaction);
        assertEquals(supplyFruitTransaction, actualFruitTransaction);
    }

}
