package core.basesyntax.servise.impl;

import core.basesyntax.servise.DataProcessorService;
import core.basesyntax.strategy.OperationStrategy;
import java.util.List;

public class DataProcessorServiceImpl implements DataProcessorService {
    private final OperationStrategy strategy;

    public DataProcessorServiceImpl(OperationStrategy strategy) {
        if (strategy == null) {
            throw new IllegalArgumentException("The operation strategy is null");
        }
        this.strategy = strategy;
    }

    @Override
    public void processingData(List<FruitTransaction> listOfTransactions) {
        if (listOfTransactions == null || listOfTransactions.isEmpty()) {
            throw new IllegalArgumentException("The List of transactions is null");
        }
        listOfTransactions.forEach(transaction -> strategy.getOperationHandler(transaction)
                        .calculation(transaction));
    }
}
