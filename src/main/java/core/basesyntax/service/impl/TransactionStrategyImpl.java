package core.basesyntax.service.impl;

import core.basesyntax.model.Operation;
import core.basesyntax.service.interfaces.TransactionStrategy;
import core.basesyntax.service.transaction.TransactionHandler;
import core.basesyntax.service.transaction.impl.BalanceTransactionHandler;
import core.basesyntax.service.transaction.impl.PurchaseTransactionHandler;
import core.basesyntax.service.transaction.impl.ReturnTransactionHandler;
import core.basesyntax.service.transaction.impl.SupplyTransactionHandler;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    public static final Map<Operation, TransactionHandler> handlers = Map.of(
            Operation.BALANCE, new BalanceTransactionHandler(),
            Operation.PURCHASE, new PurchaseTransactionHandler(),
            Operation.SUPPLY, new SupplyTransactionHandler(),
            Operation.RETURN, new ReturnTransactionHandler()
    );

    @Override
    public TransactionHandler get(Operation operation) {
        return handlers.get(operation);
    }
}
