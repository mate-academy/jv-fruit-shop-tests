package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperationActivities;
import core.basesyntax.strategy.OperationActivities;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final Map<FruitTransaction.Operation, OperationActivities>
            operationStrategyMap = new HashMap<>();
    private OperationStrategy operationStrategy;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationActivities());
        operationStrategy = new OperationStrategyImpl(operationStrategyMap);
    }

    @Test
    void get_strategy_Ok() {
        fruitTransaction = createFruits();
        int expected = 100;
        int result = operationStrategy.get(fruitTransaction.getOperation())
                .getOperation(0,fruitTransaction.getQuantity());
        Assert.assertEquals("Wrong strategy method call and result must be: "
                + expected + "\n", expected, result);
    }

    public static FruitTransaction createFruits() {
        FruitTransaction fruit = new FruitTransaction();
        fruit.setOperation(FruitTransaction.Operation.BALANCE);
        fruit.setFruit("apple");
        fruit.setQuantity(100);
        return fruit;
    }
}
