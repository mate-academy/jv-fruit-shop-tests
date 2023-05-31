package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitStrategy;
import core.basesyntax.strategy.OperationsStrategy;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitStrategyImplTest {
    private final FruitStrategy fruitStrategy;

    private FruitStrategyImplTest() {
        Map<FruitTransaction.Operation, OperationsStrategy> operationsStrategyMap = Map.of(
                BALANCE, new BalanceOperation(),
                RETURN, new ReturnOperation(),
                PURCHASE, new PurchaseOperation(),
                SUPPLY, new SupplyOperation()
        );
        fruitStrategy = new FruitStrategyImpl(operationsStrategyMap);
    }

    @Test
    void get_operationHandler_ok() {
        String expected = String.valueOf(BalanceOperation.class);
        String actual = String.valueOf(fruitStrategy
                .get(BALANCE)
                .getClass());
        Assertions.assertEquals(expected, actual);
    }
}
