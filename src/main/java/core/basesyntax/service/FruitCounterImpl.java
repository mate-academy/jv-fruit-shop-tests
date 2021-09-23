package core.basesyntax.service;

import core.basesyntax.model.FruitRecord;
import java.util.List;

public class FruitCounterImpl implements FruitCounter {
    private OperationStrategy operationStrategy;

    public FruitCounterImpl(OperationStrategy operationStrategy) {
        this.operationStrategy = operationStrategy;
    }

    @Override
    public void countFruit(List<FruitRecord> fruitRecordList) {
        String operationType;
        for (FruitRecord fruitRecord : fruitRecordList) {
            operationType = fruitRecord.getOperationType();
            operationStrategy.getOperation(operationType).apply(fruitRecord);
        }
    }
}
