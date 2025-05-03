package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyTest {
    private static final String FRUIT = "plum";
    private static final FruitTransaction.Operation OPERATION_BALANCE =
            FruitTransaction.Operation.BALANCE;
    private static final int AMOUNT = 10;
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(FRUIT, OPERATION_BALANCE, AMOUNT);
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void getOperationHandler_validInput_ok() {
        OperationHandler expected = operationHandlerMap.get(OPERATION_BALANCE);
        Assert.assertEquals(expected, operationStrategy.get(fruitTransaction.getOperation()));
    }
}
