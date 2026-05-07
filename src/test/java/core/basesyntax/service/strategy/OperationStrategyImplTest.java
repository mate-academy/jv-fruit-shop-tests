package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler balanceOperationHandler;

    @BeforeEach
    void setUp() {
        balanceOperationHandler = new BalanceOperation();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE, balanceOperationHandler);
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    }

    @Test
    void get_balanceOperation_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        assertEquals(BalanceOperation.class, operationStrategy.get(balanceTransaction).getClass());
    }

    @Test
    void get_nonExistentOperation_notOk() {
        FruitTransaction balanceTransaction = new FruitTransaction();
        balanceTransaction.setOperation(FruitTransaction.Operation.RETURN);
        assertNull(operationStrategy.get(balanceTransaction));
    }
}
