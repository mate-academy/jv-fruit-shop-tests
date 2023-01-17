package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;

public class OperationStrategyImpl implements OperationStrategy {
    private static Map<FruitTransaction.Operation, OperationHandler> strategies = new HashMap<>();

    static {
        strategies = Map.of(
                FruitTransaction.Operation.BALANCE,
                        new BalanceOperationHandler(),
                FruitTransaction.Operation.PURCHASE,
                        new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN,
                        new ReturnOperationHandler(),
                FruitTransaction.Operation.SUPPLY,
                        new SupplyOperationHandler()
        );
    }

    public OperationHandler getOperationStrategy(FruitTransaction transaction) {
        return strategies.get(transaction.getOperation());
    }
}
