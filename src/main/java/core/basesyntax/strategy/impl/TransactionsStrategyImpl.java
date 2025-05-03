package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.TransactionsStrategy;
import java.util.Map;
import java.util.stream.Collectors;

public class TransactionsStrategyImpl implements TransactionsStrategy {
    private Map<String, FruitTransaction.Operation> operationMap;

    public TransactionsStrategyImpl(Map<String, FruitTransaction.Operation> operationMap) {
        this.operationMap = operationMap;
    }

    @Override
    public FruitTransaction.Operation get(String dataFromFile) {
        dataChecker(dataFromFile);
        return operationMap.get(dataFromFile);
    }

    private void dataChecker(String dataFromFile) {
        String keyValuesOfOperationMap = operationMap.keySet().stream()
                .collect(Collectors.joining());
        String operationTypeAtDataString = dataFromFile.split(",")[0];
        if (dataFromFile == null || !keyValuesOfOperationMap.contains(operationTypeAtDataString)) {
            throw new RuntimeException("Incorrect value" + dataFromFile);
        }
    }
}
