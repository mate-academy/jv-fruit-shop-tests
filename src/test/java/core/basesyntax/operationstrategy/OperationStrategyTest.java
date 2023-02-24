package core.basesyntax.operationstrategy;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class OperationStrategyTest {
    private static final FruitTransaction.Operation BALANCE_OPERATION =
            FruitTransaction.Operation.BALANCE;
    private static final FruitTransaction.Operation RETURN_OPERATION =
            FruitTransaction.Operation.RETURN;
    private OperationHandler mockOperationHandler;
    private final FruitTransaction firstFruittransaction =
            new FruitTransaction(BALANCE_OPERATION, "banana", 70);
    private final FruitTransaction secondFruittransaction =
            new FruitTransaction(RETURN_OPERATION, "banana", 30);
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        mockOperationHandler = mock(OperationHandler.class);
        operationHandlerMap.put(BALANCE_OPERATION, mockOperationHandler);
        operationHandlerMap.put(RETURN_OPERATION, mockOperationHandler);
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void handleOperation_validBalanceTransaction_Ok() {
        //act
        operationStrategy.handleOperation(firstFruittransaction);

        //assert
        verify(mockOperationHandler).handleOperation(firstFruittransaction);
    }

    @Test
    public void handleOperation_validReturnTransaction_Ok() {
        //act
        operationStrategy.handleOperation(secondFruittransaction);

        //assert
        verify(mockOperationHandler).handleOperation(secondFruittransaction);
    }
}
