package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitService;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operation.OperationHandler;
import java.util.List;

public class FruitServiceImpl implements FruitService {
    private final FruitDao fruitDao;
    private final OperationStrategy operationStrategy;

    public FruitServiceImpl(FruitDao fruitDao, OperationStrategy operationStrategy) {
        this.fruitDao = fruitDao;
        this.operationStrategy = operationStrategy;
    }

    @Override
    public boolean setDataToStorage(List<FruitTransaction> fruitsTransactionList) {
        if (fruitsTransactionList.isEmpty()) {
            throw new RuntimeException("Fruits transaction list should contain "
                    + "at least one transaction");
        }
        for (FruitTransaction transaction : fruitsTransactionList) {
            OperationHandler operationHandler = operationStrategy.get(transaction.getOperation());
            operationHandler.changeData(transaction);
        }
        return true;
    }
}
