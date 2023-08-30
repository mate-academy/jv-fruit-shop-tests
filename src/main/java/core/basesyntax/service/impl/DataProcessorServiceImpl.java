package core.basesyntax.service.impl;

import java.util.List;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataProcessorService;
import core.basesyntax.service.OperationStrategy;

public class DataProcessorServiceImpl implements DataProcessorService {
    private final OperationStrategy operationStrategy;

    public DataProcessorServiceImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(List<FruitTransaction> inputData) {
        for (FruitTransaction transaction : inputData) {
            operationStrategy.getOperationHandler(transaction.getOperation()).execute(transaction);
        }
    }
}
