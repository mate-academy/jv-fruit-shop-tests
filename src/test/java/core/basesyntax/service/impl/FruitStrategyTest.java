package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.FruitStrategyImpl;
import core.basesyntax.strategy.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class FruitStrategyTest {
    private static FruitStrategy fruitStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperationHandler());
        fruitStrategy = new FruitStrategyImpl(operationHandlerMap);
    }

    @Test
    void get_balanceOperation_ok() {
        OperationHandler actual = fruitStrategy.get(FruitTransaction.Operation.BALANCE);
        assertInstanceOf(BalanceOperationHandler.class, actual,
                "The OperationHandler should contain element of type BalanceOperationHandler");
    }
}
