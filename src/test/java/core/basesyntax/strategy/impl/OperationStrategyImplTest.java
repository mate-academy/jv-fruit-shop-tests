package core.basesyntax.strategy.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private final OperationStrategy operationStrategy;

    private OperationStrategyImplTest() {
        Map<FruitTransaction.Operation, OperationHandler> map = Map.of(
                BALANCE, new BalanceOperationHandler(),
                SUPPLY, new SupplyOperationHandler(),
                PURCHASE, new PurchaseOperationHandler(),
                RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(map);
    }

    @Test
    void getOperationStrategy_balanceOperation_ok() {
        OperationHandler expacted = new BalanceOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(BALANCE);
        Assertions.assertEquals(expacted.getClass(), actual.getClass());
    }

    @Test
    void getOperationStrategy_purchaseOperation_ok() {
        OperationHandler expacted = new PurchaseOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(PURCHASE);
        Assertions.assertEquals(expacted.getClass(), actual.getClass());
    }

    @Test
    void getOperationStrategy_returnOperation_ok() {
        OperationHandler expacted = new ReturnOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(RETURN);
        Assertions.assertEquals(expacted.getClass(), actual.getClass());
    }

    @Test
    void getOperationStrategy_supplyOperation_ok() {
        OperationHandler expacted = new SupplyOperationHandler();
        OperationHandler actual = operationStrategy.getOperationStrategy(SUPPLY);
        Assertions.assertEquals(expacted.getClass(), actual.getClass());
    }
}
