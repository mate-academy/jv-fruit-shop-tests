package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(Map.of(
                BALANCE, new BalanceOperationHandler(),
                SUPPLY, new SupplyOperationHandler(),
                PURCHASE, new PurchaseOperationHandler(),
                RETURN, new ReturnOperationHandler()));
    }

    @Test
    void getOperationStrategy_balanceOperation_ok() {
        OperationHandler expected = new BalanceOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(BALANCE);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperationStrategy_purchaseOperation_ok() {
        OperationHandler expected = new PurchaseOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(PURCHASE);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperationStrategy_returnOperation_ok() {
        OperationHandler expected = new ReturnOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(RETURN);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getOperationStrategy_supplyOperation_ok() {
        OperationHandler expected = new SupplyOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(SUPPLY);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }
}
