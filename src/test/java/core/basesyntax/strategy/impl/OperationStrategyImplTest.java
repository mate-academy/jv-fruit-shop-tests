package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Operation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BalanceOperationHandler;
import core.basesyntax.service.strategy.impl.BuyOperationHandler;
import core.basesyntax.service.strategy.impl.ReturnOperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static OperationHandler expected;
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        expected = new SupplyOperationHandler();
        operationStrategy = new OperationStrategyImpl(Map.of(
                Operation.BALANCE, new BalanceOperationHandler(),
                Operation.PURCHASE, new BuyOperationHandler(),
                Operation.RETURN, new ReturnOperationHandler(),
                Operation.SUPPLY, expected));
    }

    @Test
    void getOperationHandler_findHandler_ok() {
        OperationHandler actual = operationStrategy.getOperationHandler(Operation.SUPPLY);
        assertEquals(expected, actual);
    }
}
