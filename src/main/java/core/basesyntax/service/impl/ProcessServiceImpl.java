package core.basesyntax.service.impl;

import core.basesyntax.model.ActivityType;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.ProcessService;
import core.basesyntax.strategy.OperationsHandler;
import java.util.List;
import java.util.Map;

public class ProcessServiceImpl implements ProcessService {
    private Map<ActivityType, OperationsHandler> processedStoreHandler;

    public ProcessServiceImpl(Map<ActivityType, OperationsHandler> storeHandler) {
        this.processedStoreHandler = storeHandler;
    }

    @Override
    public void process(List<FruitTransaction> fruitTransactionList) {
        for (FruitTransaction fruitTransaction : fruitTransactionList) {
            processedStoreHandler.get(fruitTransaction.getActivityType())
                    .useOperation(fruitTransaction);
        }
    }
}
