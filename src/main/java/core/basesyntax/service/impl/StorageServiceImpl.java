package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.StorageService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class StorageServiceImpl implements StorageService {
    private Map<Operation, OperationHandler> operations;

    public StorageServiceImpl(Map<Operation, OperationHandler> operations) {
        this.operations = operations;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactions) {
        for (FruitTransaction fruitTransaction : fruitTransactions) {
            operations.get(fruitTransaction.getOperation()).execute(fruitTransaction);
        }
    }
}
