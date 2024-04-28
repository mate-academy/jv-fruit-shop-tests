package core.basesyntax.service.operation;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap = new HashMap<>();
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategy(operationHandlerMap);
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
    }

    @Test
    void get_Ok() {
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(BalanceOperationHandler.class, actual.getClass());
    }

    @Test
    void get_Null_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy.get(null));
    }

    @Test
    void get_wrongOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class,
                () -> operationStrategy.get(FruitTransaction.Operation.SUPPLY));
    }
}
