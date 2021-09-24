package core.service.operation;

import core.model.FruitRecord;
import core.model.OperationType;
import java.util.HashMap;
import java.util.Map;

public class ReturnOperationTypeHandler implements OperationTypeHandler {
    private Map<String, Integer> mapFruit = new HashMap<>();

    public ReturnOperationTypeHandler() {
    }

    @Override
    public OperationType getOperationType(String string) {
        return OperationType.RETURN;
    }

    @Override
    public int getUpdateAmount(FruitRecord fruitRecord, int previousAmount) {
        return previousAmount + fruitRecord.getAmount();
    }
}
