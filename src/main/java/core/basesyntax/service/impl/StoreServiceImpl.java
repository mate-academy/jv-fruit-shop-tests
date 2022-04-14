package core.basesyntax.service.impl;

import core.basesyntax.model.FruitRecordDto;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.StoreService;
import java.util.List;

public class StoreServiceImpl implements StoreService {
    private OperationStrategy strategy;

    public StoreServiceImpl(OperationStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public void doInstruction(List<FruitRecordDto> info) {
        for (FruitRecordDto record : info) {
            strategy.get(record.getOperationType()).apply(record);
        }
    }
}
