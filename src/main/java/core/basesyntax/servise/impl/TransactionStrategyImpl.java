package core.basesyntax.servise.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.servise.TransactionStrategy;
import core.basesyntax.servise.transaction.TransactionHandler;
import core.basesyntax.servise.transaction.impl.BalanceTransactionHandler;
import core.basesyntax.servise.transaction.impl.PurchaseTransactionHandler;
import core.basesyntax.servise.transaction.impl.ReturnTransactionHandler;
import core.basesyntax.servise.transaction.impl.SupplyTransactionHandler;
import java.util.Map;

public class TransactionStrategyImpl implements TransactionStrategy {
    private final Map<String, TransactionHandler> transactionHandlers;

    {
        transactionHandlers = Map.of(
                Transaction.Operation.BALANCE.getType(), new BalanceTransactionHandler(),
                Transaction.Operation.SUPPLY.getType(), new SupplyTransactionHandler(),
                Transaction.Operation.PURCHASE.getType(), new PurchaseTransactionHandler(),
                Transaction.Operation.RETURN.getType(), new ReturnTransactionHandler()
        );
    }

    @Override
    public TransactionHandler get(String operation) {
        return transactionHandlers.get(operation);
    }
}
