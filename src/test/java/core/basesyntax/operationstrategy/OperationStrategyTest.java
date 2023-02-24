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
    private static final String FRUIT = "banana";
    private static final Integer QUANTITY = 30;
    private static final FruitTransaction.Operation OPERATION = FruitTransaction.Operation.BALANCE;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private OperationHandler mockOperationHandler;
    private FruitTransaction fruitTransaction;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        mockOperationHandler = mock(OperationHandler.class);
        operationHandlerMap.put(OPERATION, mockOperationHandler);
        fruitTransaction = new FruitTransaction(OPERATION, FRUIT, QUANTITY);
        operationStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    public void handleOperation_validTransaction_Ok() {
        //act
        operationStrategy.handleOperation(fruitTransaction);

        //assert
        verify(mockOperationHandler).handleOperation(fruitTransaction);
    }
}
