package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.opationstrategy.OperationHandler;
import core.basesyntax.opationstrategy.OperationStrategy;
import core.basesyntax.opationstrategy.OperationStrategyImpl;
import core.basesyntax.operations.BalanceOperation;
import core.basesyntax.operations.FruitTransaction;
import core.basesyntax.operations.PurchaseOperation;
import core.basesyntax.operations.ReturnOperation;
import core.basesyntax.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        handlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        handlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        handlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void getHandler_shouldReturnCorrectHandler() {
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperation);
        assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.RETURN)
                instanceof ReturnOperation);
    }
}
