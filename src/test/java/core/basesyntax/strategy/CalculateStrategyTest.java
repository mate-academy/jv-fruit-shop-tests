package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.Map;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class CalculateStrategyTest {
    private final Map<FruitTransaction.Operation, OperationHandler>
            correspondenceTable = Map.of(
            FruitTransaction.Operation.BALANCE, new OperationHandlerBalance(),
            FruitTransaction.Operation.SUPPLY, new OperationHandlerIn(),
            FruitTransaction.Operation.RETURN, new OperationHandlerIn(),
            FruitTransaction.Operation.PURCHASE, new OperationHandlerOut());

    @Test
    public void getOperationByOperationCodeIs_Ok() {
        CalculateStrategy calculateStrategy = new CalculateStrategy(correspondenceTable);
        OperationHandler handlerBalance = calculateStrategy.getHandler(FruitTransaction.Operation
                .BALANCE);
        Assertions.assertTrue(handlerBalance instanceof OperationHandlerBalance);
        OperationHandler handlerReturn = calculateStrategy.getHandler(FruitTransaction.Operation
                .RETURN);
        Assertions.assertTrue(handlerReturn instanceof OperationHandlerIn);
        OperationHandler handlerPurchase = calculateStrategy.getHandler(FruitTransaction.Operation
                .PURCHASE);
        Assertions.assertTrue(handlerPurchase instanceof OperationHandlerOut);
    }

    @Test
    public void testGetHandler() {
        CalculateStrategy calculateStrategy = new CalculateStrategy(correspondenceTable);
        OperationHandler operationHandlerOut = calculateStrategy.getHandler(FruitTransaction
                .Operation.PURCHASE);
        assertEquals(OperationHandlerOut.class, operationHandlerOut.getClass());
        OperationHandler operationHandlerIn = calculateStrategy.getHandler(FruitTransaction
                .Operation.SUPPLY);
        assertEquals(OperationHandlerIn.class, operationHandlerIn.getClass());
    }
}
