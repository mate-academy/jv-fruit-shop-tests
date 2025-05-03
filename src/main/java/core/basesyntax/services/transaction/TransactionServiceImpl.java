package core.basesyntax.services.transaction;

import core.basesyntax.services.OperationStrategy;
import core.basesyntax.services.transaction.model.ProductTransaction;
import java.util.List;

public class TransactionServiceImpl implements TransactionService {
    private final OperationStrategy strategy;

    public TransactionServiceImpl(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void process(List<ProductTransaction> productTransactions) {
        productTransactions.forEach(transaction ->
                strategy.get(transaction.getOperation()).handle(transaction));
    }
}
