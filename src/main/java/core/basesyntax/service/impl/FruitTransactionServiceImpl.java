package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.strategy.FruitTransactionStrategy;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private FruitTransactionStrategy strategy;
    private Storage fruitsStorage;

    public FruitTransactionServiceImpl(FruitTransactionStrategy strategy, Storage fruitsStorage) {
        this.strategy = strategy;
        this.fruitsStorage = fruitsStorage;
    }

    public void processTransaction(List<FruitTransaction> transactions) {
        if (transactions.isEmpty()) {
            throw new RuntimeException("Can't process transactions for empty input list");
        }
        for (FruitTransaction transaction : transactions) {
            OperationHandler handler = strategy.get(transaction.getOperation());
            handler.processOperation(transaction, fruitsStorage);
        }
    }
}
