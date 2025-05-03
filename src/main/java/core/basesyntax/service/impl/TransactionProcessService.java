package core.basesyntax.service.impl;

import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.OperationStrategy;

public class TransactionProcessService implements TransactionService {
    private static final int OPERATION = 0;
    private static final String COMMA = ",";
    private OperationStrategy operationStrategy;

    public TransactionProcessService(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void process(String[] data) {
        for (String datum : data) {
            String[] check = datum.split(COMMA);
            operationStrategy.get(check[OPERATION]).processCommand(check);
        }
    }
}
