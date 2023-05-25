package core.basesyntax.strategy;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Operation;
import core.basesyntax.strategy.impl.BalanceImpl;
import core.basesyntax.strategy.impl.PurchaseImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static OperationStrategy operationHandlerStrategy;
    private static Map<Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerStrategy = new OperationStrategy(operationHandlerMap);
        operationHandlerMap = new HashMap<>();
    }

    @BeforeEach
    void setUp() {
        operationHandlerStrategy = new OperationStrategy(operationHandlerMap);
    }

    @Test
    void getOperation_BalanceOperation_ok() {
        operationHandlerMap.put(Operation.BALANCE, new BalanceImpl());
        Class expected = BalanceImpl.class;
        Class actual = operationHandlerStrategy.getHandler(Operation.BALANCE)
                        .getClass();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getOperation_PurchaseOperation_ok() {
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseImpl());
        Class expected = PurchaseImpl.class;
        Class actual = operationHandlerStrategy.getHandler(Operation.PURCHASE)
                .getClass();
        Assertions.assertEquals(expected, actual);
    }

    @AfterAll
    public static void clear() {
        FruitStorage.fruits.clear();
    }

}
