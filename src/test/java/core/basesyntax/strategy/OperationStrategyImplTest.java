package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.activities.BalanceHandler;
import core.basesyntax.strategy.activities.OperationHandler;
import core.basesyntax.strategy.activities.PurchaseHandler;
import core.basesyntax.strategy.activities.ReturnHandler;
import core.basesyntax.strategy.activities.SupplyHandler;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap =
            Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseHandler(),
            FruitTransaction.Operation.RETURN, new ReturnHandler()
    );
    private final OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationHandlerMap);

    @Test
    void getOperation_Ok() {
        for (FruitTransaction.Operation operation : operationHandlerMap.keySet()) {
            OperationHandler expected = operationHandlerMap.get(operation);
            OperationHandler actual = operationStrategy.get(operation);

            assertEquals(expected, actual);
        }
    }

    @Test
    void getOperation_Null_InvalidOperation() {
        FruitTransaction.Operation invalidOperation;
        try {
            invalidOperation = FruitTransaction.Operation.valueOf("INVALID");
        } catch (IllegalArgumentException e) {
            invalidOperation = null;
        }
        OperationHandler handler = null;
        if (invalidOperation != null) {
            handler = operationStrategy.get(invalidOperation);
        }

        assertNull(handler);
    }
}
