package core.basesyntax.service.strategy.impl;

import core.basesyntax.model.Transaction;
import core.basesyntax.repository.StorageRepository;
import core.basesyntax.service.strategy.OperationStrategy;

public class BalanceOperation implements OperationStrategy {
    private static final String NEGATIVE_VALUE_MESSAGE
            = "Unsupported value for BALANCE operation: ";
    private StorageRepository repository;

    public BalanceOperation(StorageRepository repository) {
        this.repository = repository;
    }

    @Override
    public void execute(Transaction transaction) {
        int value = transaction.getValue();
        if (value < 0) {
            throw new RuntimeException(NEGATIVE_VALUE_MESSAGE + value);
        }
        repository.add(transaction);
    }
}
