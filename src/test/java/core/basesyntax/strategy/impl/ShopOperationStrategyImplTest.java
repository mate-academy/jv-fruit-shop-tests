package core.basesyntax.strategy.impl;

import core.basesyntax.handler.ShopOperationHandler;
import core.basesyntax.handler.impl.BalanceOperationHandler;
import core.basesyntax.strategy.ShopOperationStrategy;
import core.basesyntax.utility.Operation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ShopOperationStrategyImplTest {
    private static ShopOperationStrategy shopOperationStrategy;
    private static final Operation BALANCE_OPERATION = Operation.BALANCE;

    @BeforeAll
    static void beforeAll() {
        Map<Operation, ShopOperationHandler> shopOperationHandlerMap = new HashMap<>();
        shopOperationHandlerMap.put(Operation.BALANCE,
                new BalanceOperationHandler());
        shopOperationStrategy = new ShopOperationStrategyImpl(shopOperationHandlerMap);

    }

    @Test
    void get_validData_Ok() {
        ShopOperationHandler expectedHandler = shopOperationStrategy.get(BALANCE_OPERATION);
        assertTrue(expectedHandler instanceof BalanceOperationHandler);
    }
}