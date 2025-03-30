package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperation;
import core.basesyntax.service.strategy.impl.PurchaseOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;
    private OperationHandler balanceOperation;
    private OperationHandler purchaseOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceOperation);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseOperation);
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void get_existingKey_ok() {
        OperationHandler expected = balanceOperation;
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertEquals(expected, actual);
    }

    @Test
    void get_notExistingKey_notOk() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        assertNull(actual);
    }

    @Test
    void get_nullKey_notOk() {
        OperationHandler actual = operationStrategy.get(null);
        assertNull(actual);
    }
}
