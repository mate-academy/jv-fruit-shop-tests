package strategy;

import static org.junit.jupiter.api.Assertions.*;

import model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, TransactionHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceHandler());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyHandler());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandler());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnHandler());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getStrategy_WhenValidOperation_ReturnsCorrectHandler() {
        assertInstanceOf(BalanceHandler.class, operationStrategy.getStrategy(FruitTransaction.Operation.BALANCE));
        assertInstanceOf(SupplyHandler.class, operationStrategy.getStrategy(FruitTransaction.Operation.SUPPLY));
        assertInstanceOf(PurchaseHandler.class, operationStrategy.getStrategy(FruitTransaction.Operation.PURCHASE));
        assertInstanceOf(ReturnHandler.class, operationStrategy.getStrategy(FruitTransaction.Operation.RETURN));
    }

    @Test
    void getStrategy_WhenNullOperation_ThrowsException() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> operationStrategy.getStrategy(null));
        assertEquals("Unknown operation: null", exception.getMessage());
    }

}
