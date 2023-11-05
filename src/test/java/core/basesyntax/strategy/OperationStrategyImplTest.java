package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceTypeHandler;
import core.basesyntax.strategy.operation.OperationHandlers;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandlers> operationHandlersMap =
            new HashMap<>() {
                {
                    put(FruitTransaction.Operation.BALANCE, new BalanceTypeHandler());
                }
            };

    private static final OperationStrategy OPERATION_STRATEGY
            = new OperationStrategyImpl(operationHandlersMap);
    private static final OperationHandlers BALANCE_TYPE_HANDLER = new BalanceTypeHandler();

    private static final FruitTransaction.Operation VALID_OPERATION
            = FruitTransaction.Operation.BALANCE;

    @Test
    void getHandler_ValidData_Ok() {
        assertEquals(BALANCE_TYPE_HANDLER.getClass(),OPERATION_STRATEGY
                .getHandler(VALID_OPERATION).getClass());
    }
}
