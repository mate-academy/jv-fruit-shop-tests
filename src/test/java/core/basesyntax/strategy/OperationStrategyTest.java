package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.Operation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<Operation, OperationHandler> mapOperation = new HashMap<>();
        mapOperation.put(Operation.BALANCE, new BalanceOperationHandler());
        mapOperation.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationStrategy = new OperationStrategy(mapOperation);
    }

    @Test
    void getStrategy_existOperation_ok() {
        OperationHandler strategy = operationStrategy.getStrategy(Operation.BALANCE);
        assertNotNull(strategy);
        assertTrue(strategy instanceof BalanceOperationHandler);
    }
}

