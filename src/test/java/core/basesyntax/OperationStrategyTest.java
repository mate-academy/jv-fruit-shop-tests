package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.handler.OperationHandler;
import core.basesyntax.handler.OperationStrategy;
import core.basesyntax.handler.OperationStrategyImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void operationStrategy_returnsCorrectHandlerForSupply_ok() {
        OperationHandler handler = operationStrategy.getHandler(
                FruitTransaction.Operation.SUPPLY);

        assertTrue(handler instanceof SupplyOperation,
                "Handler for SUPPLY should be of type SupplyOperation.");
    }

    @Test
    void operationStrategy_returnsCorrectHandlerForPurchase_ok() {
        OperationHandler handler = operationStrategy.getHandler(
                FruitTransaction.Operation.PURCHASE);

        assertTrue(handler instanceof PurchaseOperation,
                "Handler for Purchase should be of type PurchaseOperation.");
    }

    @Test
    void operationStrategy_returnsCorrectHandlerForBalance_ok() {
        OperationHandler handler = operationStrategy.getHandler(
                FruitTransaction.Operation.BALANCE);

        assertTrue(handler instanceof BalanceOperation,
                "Handler for Balance should be of type BalanceOperation.");
    }

    @Test
    void operationStrategy_returnsCorrectHandlerForReturn_ok() {
        OperationHandler handler = operationStrategy.getHandler(
                FruitTransaction.Operation.RETURN);

        assertTrue(handler instanceof ReturnOperation,
                "Handler for Return should be of type ReturnOperation.");
    }

    @Test
    void operationStrategy_empty_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> operationStrategy.getHandler(null),
                "Expected IllegalArgumentException when operation is null.");
    }
}
