package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();

        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, supplyOperation);
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getOperationHandler_validOperation_returnsCorrectHandler() {
        OperationHandler actual = operationStrategy.getOperationHandler(
                FruitTransaction.Operation.SUPPLY);
        assertSame(supplyOperation, actual);
    }

    @Test
    void getOperationHandler_operationDoesNotExist_throwsException() {
        assertThrows(RuntimeException.class,
                () -> operationStrategy.getOperationHandler(
                        FruitTransaction.Operation.PURCHASE));
    }
}
