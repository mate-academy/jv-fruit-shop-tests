package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationActivity;
import core.basesyntax.strategy.OperationStrategy;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl();
    }

    @Test
    public void get_validData_Ok() {
        OperationActivity actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        assertNotNull(actual);
        assertTrue(actual instanceof BalanceOperationActivity);
    }
}
