package core.basesyntax.service.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProcessDataService;
import core.basesyntax.strategy.OperationHandler;
import java.util.List;
import java.util.Map;

public class ProcessDataServiceImpl implements ProcessDataService {
    private Map<FruitTransaction.Operation, OperationHandler> operations;

    public ProcessDataServiceImpl(Map<FruitTransaction.Operation, OperationHandler> operations) {
        this.operations = operations;
    }

    @Override
    public void processData(List<FruitTransaction> list) {
        for (FruitTransaction fruitTransaction : list) {
            operations.get(fruitTransaction.getOperation()).apply(fruitTransaction);
        }
    }
}
