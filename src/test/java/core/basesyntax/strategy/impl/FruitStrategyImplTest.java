package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.OperationsStrategy;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class FruitStrategyImplTest {
    private static FruitStrategy fruitStrategy;

    @BeforeAll
    static void beforeAll() {
        Map<FruitTransaction.Operation, OperationsStrategy> operationsStrategyMap = new HashMap<>();
        operationsStrategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationsStrategyMap.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationsStrategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationsStrategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        fruitStrategy = new FruitStrategyImpl(operationsStrategyMap);
    }

    @Test
    void get_operationHandler_ok() {
        String expected = String.valueOf(BalanceOperation.class);
        String actual = String.valueOf(fruitStrategy
                .get(FruitTransaction.Operation.BALANCE)
                .getClass());
        Assertions.assertEquals(expected, actual);
    }
}
