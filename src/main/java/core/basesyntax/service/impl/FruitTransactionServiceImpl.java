package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;

public class FruitTransactionServiceImpl implements FruitTransactionService {
    private FruitDao dao;
    private OperationStrategy operationStrategy;

    public FruitTransactionServiceImpl(FruitDao dao, OperationStrategy operationStrategy) {
        this.dao = dao;
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void processData(FruitTransaction[] transactions) {
        for (FruitTransaction transaction : transactions) {
            OperationHandler operationHandler =
                    operationStrategy.getOperationHandler(transaction.getOperation());
            operationHandler.operation(transaction, dao);
        }
    }
}
