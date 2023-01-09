package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.Map;

public class OperationStrategy {
    private static final Map<FruitTransaction.Operation,
            OperationHandler> OPERATIONS_MAP;

    static {
        OPERATIONS_MAP = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
                FruitTransaction.Operation.RETURN, new ReturnOperationHandler());
    }

    public OperationHandler getHandler(FruitTransaction transaction) {
        if (transaction == null || transaction.getOperation() == null) {
            throw new RuntimeException("Can't find handler for current transaction " + transaction);
        }
        return OPERATIONS_MAP.get(transaction.getOperation());
    }
}
