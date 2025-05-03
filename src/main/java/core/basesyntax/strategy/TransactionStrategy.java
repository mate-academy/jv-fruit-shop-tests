package core.basesyntax.strategy;

import core.basesyntax.model.StorageTransaction;
import java.util.Map;

public class TransactionStrategy implements Strategy {
    private Map<StorageTransaction.Operation, TransactionHandler> handlers;

    public TransactionStrategy(Map<StorageTransaction.Operation, TransactionHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public TransactionHandler getHandler(StorageTransaction transaction) {
        if (handlers.get(transaction.getOperation()) == null) {
            throw new RuntimeException("Can't find handler for this operation: "
                    + transaction.getOperation().name());
        }
        return handlers.get(transaction.getOperation());
    }

}
