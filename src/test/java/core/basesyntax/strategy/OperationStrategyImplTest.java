package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static final Operation EXISTING_OPERATION = Operation.BALANCE;
    private Map<Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(Operation.RETURN, new ReturnOperation());
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void get_nonExistingOperation_notOk() {
        assertThrows(RuntimeException.class, () -> operationStrategy.get(null),
                "You should throw an exception when operation is null or does not exist");
    }

    @Test
    void get_ExistingOperation_ok() {
        assertEquals(operationHandlers.get(Operation.BALANCE), operationStrategy
                .get(EXISTING_OPERATION));
    }
}
