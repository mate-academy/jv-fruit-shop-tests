package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.impl.BalanceOperation;
import core.basesyntax.operation.impl.OperationHandler;
import core.basesyntax.operation.impl.PurchaseOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlers
            = new HashMap<>() {{
                    put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
                    put(null, null);
                    put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
                    }};
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

    @Test
    void getService_null_NotOk() {
        assertNull(operationStrategy.getService(null));
    }

    @Test
    void getService_nonExistingOperation_NotOk() {
        assertNull(operationStrategy.getService(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getService_Ok() {
        assertTrue(operationStrategy.getService(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
    }
}
